package com.weike.foodsafe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.weike.common.constant.OrderConstant;
import com.weike.common.exception.NoInputSourceException;
import com.weike.common.utils.JwtHelper;
import com.weike.foodsafe.dao.DistributionDao;
import com.weike.foodsafe.dao.ShoppingcarDao;
import com.weike.foodsafe.dao.SupplierDao;
import com.weike.foodsafe.entity.*;
import com.weike.foodsafe.service.*;
import com.weike.foodsafe.vo.GoodsSourceVo;
import com.weike.foodsafe.vo.OrderGoodsVo;
import com.weike.foodsafe.vo.OrderVo;
import com.weike.foodsafe.vo.order.OrderCheckOutVo;
import com.weike.foodsafe.vo.order.OrderReceiveVo;
import com.weike.foodsafe.vo.order.PurchaserOrderResVo;
import com.weike.system.entity.UserEntity;
import com.weike.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.Query;

import com.weike.foodsafe.dao.OrderDao;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private DistributionDao distributionDao;

    @Autowired
    private PurchaserService purchaserService;

    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;

    @Autowired
    private ClassificationService classificationService;

    @Autowired
    private ShoppingcarDao shoppingcarDao;

    @Autowired
    private AttachmentsService attachmentsService;

    @Autowired
    private SupplierDao supplierDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 获取订单
     *
     * @param params
     * @return
     */
    @Override
    public PageUtils queryOrderPage(Map<String, Object> params, String token) {
        // 取出 params 中的筛选条件
        String orderNo = (String) params.get("orderNo");
        String startDate = (String) params.get("startDate");
        String endDate = (String) params.get("endDate");
        String status = (String) params.get("status");
        String purchaserName = (String) params.get("purchaserName");

        LambdaQueryWrapper<OrderEntity> wrapper = new LambdaQueryWrapper<>();

        // 构造查询条件
        String userId = jwtHelper.getUserId(token);

        if (!StringUtils.isBlank(orderNo)) {
            wrapper.like(OrderEntity::getOrderno, orderNo);
        }
        if (!StringUtils.isBlank(startDate)) {
            wrapper.ge(OrderEntity::getAddtime, startDate);
        }
        if (!StringUtils.isBlank(endDate)) {
            wrapper.le(OrderEntity::getAddtime, endDate);
        }
        if (!StringUtils.isBlank(purchaserName)) {
            PurchaserEntity purchaserEntity = purchaserService.getOne(new LambdaQueryWrapper<PurchaserEntity>()
                    .eq(PurchaserEntity::getCompanyname, purchaserName));
            if (purchaserEntity != null) {
                wrapper.le(OrderEntity::getPurchaserid, purchaserEntity.getPurchaserid());
            } else wrapper.le(OrderEntity::getPurchaserid, -1);
        }

        if (!StringUtils.isBlank(status)) {
            // 当status为-1时代表查询全部
            if (!status.equals("-1")) {
                wrapper.eq(OrderEntity::getStatus, status);
            }
        }

        // 查询当前账号属于哪个配送商
        String distributionId = distributionDao.getDistributionIdByUserId(userId);
        wrapper.eq(OrderEntity::getDistributionid, distributionId);

        // 返回的结果
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                wrapper
        );
        List<OrderEntity> pageRecords = page.getRecords();

        List<OrderVo> orderVoList = null;

        if (pageRecords.size() > 0) {
           orderVoList = getOrderList(pageRecords);
        }

        // 设置返回结果
        PageUtils pageUtils = new PageUtils(page);
        pageUtils.setList(orderVoList);
        return pageUtils;
    }

    /**
     * 获取订单分页数据
     * @param orderEntityList
     * @return
     */
    @Override
    public List<OrderVo> getOrderList(List<OrderEntity> orderEntityList ) {
        // 收集订单id
        List<String> orderIds = orderEntityList.stream().map(OrderEntity::getOrderid).collect(Collectors.toList());

        // 收集采购商id
        List<String> purchaserIds = orderEntityList.stream().map(OrderEntity::getPurchaserid).collect(Collectors.toList());

        // 收集配送商id
        List<String> distributionIds = orderEntityList.stream().map(OrderEntity::getDistributionid).collect(Collectors.toList());

        // 查出所需的全部配送商实体
        List<DistributionEntity> distributionEntityList;
        if(distributionIds.size() > 0) {
            distributionEntityList = distributionDao.selectBatchIds(distributionIds);
        } else {
            distributionEntityList = new ArrayList<>();
        }


        // 查出所需的全部采购商实体
        List<PurchaserEntity> purchaserEntityList;
        if (purchaserIds.size() > 0) {
            purchaserEntityList = purchaserService.listByIds(purchaserIds);
        } else {
            purchaserEntityList = new ArrayList<>();
        }


        // 查出当前所有的订单详情
        List<OrderDetailEntity> orderDetailEntityList;
        if (orderIds.size() > 0) {
            orderDetailEntityList = orderDetailService.list(new LambdaQueryWrapper<OrderDetailEntity>()
                    .in(OrderDetailEntity::getOrderid, orderIds));
        } else {
            orderDetailEntityList = new ArrayList<>();
        }


        // 收集所有供应商id
        List<String> supplierIds = orderDetailEntityList.stream().map(OrderDetailEntity::getSupplierid).collect(Collectors.toList());

        // 查出所有供应商实体类
        List<SupplierEntity> supplierEntities;
        if (supplierIds.size() > 0) {
            supplierEntities = supplierDao.selectBatchIds(supplierIds);
        } else {
            supplierEntities = new ArrayList<>();
        }

        // 查出所需的所有商品详情id
        List<String> goodsIds = orderDetailEntityList.stream().map(OrderDetailEntity::getGoodsid).collect(Collectors.toList());

        // 查出所有的商品信息
        List<GoodsEntity> goodsEntityList;
        if (goodsIds.size() > 0) {
            goodsEntityList = goodsService.listByIds(goodsIds);
        } else {
            goodsEntityList = new ArrayList<>();
        }


        // 查出所有配送商及采购商证件信息
        List<AttachmentsEntity> attachmentsEntities;
        if (distributionIds.size() > 0) {
            attachmentsEntities = attachmentsService.list(new LambdaQueryWrapper<AttachmentsEntity>()
                    .in(AttachmentsEntity::getRelationid, distributionIds)
                    .or()
                    .in(AttachmentsEntity::getRelationid, purchaserIds));
        } else {
            attachmentsEntities = new ArrayList<>();
        }


        // 构造Vo
        List<OrderVo> orderVoList = orderEntityList.stream().map(orderEntity -> {
            OrderVo orderVo = new OrderVo();
            BeanUtils.copyProperties(orderEntity, orderVo);

            // 查询当前订单的采购商实体
            List<PurchaserEntity> purchaserEntities = purchaserEntityList.stream().filter(purchaserEntity
                            -> purchaserEntity.getPurchaserid().equals(orderEntity.getPurchaserid()))
                    .collect(Collectors.toList());
            orderVo.setPurchaserName(purchaserEntities.get(0).getCompanyname());

            // 查出所有订单相关证件照片
                // 查询采购商证件信息
            List<String> puchaserImgList = attachmentsEntities.stream().filter(attachmentsEntity
                            -> attachmentsEntity.getRelationid().equals(orderEntity.getPurchaserid()))
                    .map(AttachmentsEntity::getUrl)
                    .collect(Collectors.toList());
                // 设置采购商相关证件列表
            orderVo.setPurchaserPzUrl(puchaserImgList);
                // 查询配送商证件信息
            List<String> distributionImgList = attachmentsEntities.stream().filter(attachmentsEntity
                            -> attachmentsEntity.getRelationid().equals(orderEntity.getDistributionid()))
                    .map(AttachmentsEntity :: getUrl)
                    .collect(Collectors.toList());
                // 设置配送商证件列表
            orderVo.setDistributionPzUrl(distributionImgList);

            // 查询当前订单的配送商实体类
            List<DistributionEntity> distributionEntities = distributionEntityList.stream().filter(distributionEntity
                            -> distributionEntity.getDistributionid().equals(orderEntity.getDistributionid()))
                    .collect(Collectors.toList());

            // 如果配送商存在
            if (distributionEntities.size() > 0) {
                DistributionEntity distributionEntity = distributionEntities.get(0);
                orderVo.setDistributionCompanyName(distributionEntity.getCompanyname());
                orderVo.setDistributionAddr(distributionEntity.getAddr());
                orderVo.setDistributionPerson(distributionEntity.getName());
                orderVo.setDistributionTel(distributionEntity.getPhone());
            }

            // 查询当前订单的订单详情
            List<OrderDetailEntity> orderDetailEntities = orderDetailEntityList.stream().filter(orderDetailEntity
                            -> orderDetailEntity.getOrderid().equals(orderEntity.getOrderid()))
                    .collect(Collectors.toList());

            // 构造订单详情
            List<OrderVo.OrderDetailVo> orderDetailVos = orderDetailEntities.stream().map(orderDetailEntity -> {
                OrderVo.OrderDetailVo orderDetailVo = new OrderVo.OrderDetailVo();
                BeanUtils.copyProperties(orderDetailEntity, orderDetailVo);
                // 找出所需供应商实体类
                List<SupplierEntity> supplier = supplierEntities.stream().filter(supplierEntity
                                -> supplierEntity.getSupplierid().equals(orderDetailEntity.getSupplierid()))
                        .collect(Collectors.toList());

                // 过滤出需要的商品实体类
                List<GoodsEntity> goodsEntities = goodsEntityList.stream()
                        .filter(goodsEntity -> goodsEntity.getGoodsid().equals(orderDetailEntity.getGoodsid()))
                        .collect(Collectors.toList());

                if (supplier.size() > 0) {
                    orderDetailVo.setSupplierEntity(supplier.get(0));
                }

                orderDetailVo.setGoodsName(goodsEntities.get(0).getGoodsname());
                return orderDetailVo;
            }).collect(Collectors.toList());

            orderVo.setChildren(orderDetailVos);
            return orderVo;
        }).collect(Collectors.toList());
        return orderVoList;
    }

    /**
     * 受理/拒绝订单
     *
     * @param orderid
     */
    @Override
    public void acceptOrder(String orderid, String status, String token) {
        String userId = jwtHelper.getUserId(token);

        if (!StringUtils.isBlank(userId)) {

            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setOrderid(orderid);
            // 为1受理订单 否则拒绝
            if (status.equals("1")) {
                orderEntity.setConfirmsignuser(userId);
                orderEntity.setConfirmsigntime(new Date());
                orderEntity.setStatus(OrderConstant.UN_SEND.getCode() + "");
            } else {
                orderEntity.setStatus(OrderConstant.REFUSE_ACCEPT.getCode() + "");
            }

            this.updateById(orderEntity);
        }
    }

    /**
     * 订单出库
     *
     * @param orderid
     * @param token
     */
    @Override
    public void orderOut(String orderid, String token) throws NoInputSourceException {

        OrderEntity orderEntity = this.getById(orderid);

        if (orderEntity != null) {
            // 获取全部订单详情实体类
            List<OrderDetailEntity> orderDetailEntities = orderDetailService.list(
                    new LambdaQueryWrapper<OrderDetailEntity>()
                            .eq(OrderDetailEntity::getOrderid, orderEntity.getOrderid())
            );

            ArrayList<String> noInputNameList = new ArrayList<>();
            // 检查订单详情中是否已填报来源
            boolean isInput = true;
            for (OrderDetailEntity orderDetailEntity : orderDetailEntities) {
                System.out.println(orderDetailEntity.getSupplierid());
                if (StringUtils.isBlank(orderDetailEntity.getSupplierid())) {
                    isInput = false;
                    GoodsEntity goods = goodsService.getById(orderDetailEntity.getGoodsid());
                    noInputNameList.add(goods.getGoodsname());
                }
            }
            // 如果发现为填写来源则抛出未填写来源异常
            if (!isInput) {
                throw new NoInputSourceException("订单出库异常", noInputNameList);
            } else {
                // 更新订单状态为已发货
                OrderEntity order = new OrderEntity();
                order.setOrderid(orderEntity.getOrderid());
                order.setStatus(OrderConstant.SEND.getCode() + "");
                this.updateById(order);
            }

        }

    }

    /**
     * 填写订单商品来源
     *
     * @param goodsSourceVo
     */
    @Override
    public void orderGoodsSource(GoodsSourceVo goodsSourceVo) {
        String orderId = goodsSourceVo.getOrderId();
        System.out.println(goodsSourceVo);
        List<OrderDetailEntity> orderDetailEntities = null;
        // 如果OrderIdList 不为空则代表要批量添加来源
        if (goodsSourceVo.getOrderIdList() != null && goodsSourceVo.getOrderIdList().size() > 0) {
            List<String> orderIdList = goodsSourceVo.getOrderIdList();

            // 查询出所有order的order详情
            orderDetailEntities = orderDetailService.list(new LambdaQueryWrapper<OrderDetailEntity>()
                    .in(OrderDetailEntity::getOrderid, orderIdList));

        } else {
            // 查询出所有订单详情
            orderDetailEntities = orderDetailService.listByIds(goodsSourceVo.getOrderDetailIds());
        }

        // 构造更新对象
        List<OrderDetailEntity> orderDetailEntityList = orderDetailEntities.stream().map(orderDetailEntity -> {
            OrderDetailEntity orderDetail = new OrderDetailEntity();
            orderDetail.setOrderdetailid(orderDetailEntity.getOrderdetailid());
            orderDetail.setSupplierid(goodsSourceVo.getSupplierId());
            orderDetail.setPzurl(goodsSourceVo.getPzurl());
            orderDetail.setPurchaseTime(goodsSourceVo.getPurchaseTime());
            return orderDetail;
        }).collect(Collectors.toList());

        // 批量更新
        orderDetailService.updateBatchById(orderDetailEntityList);
    }


    /**
     * 获取订单order下的全商品vo
     *
     * @param orderid
     * @return
     */
    @Override
    public List<OrderGoodsVo> getGoodsByOrderId(String orderid) {

        // 查询当前订单下的全部订单详情实体类
        List<OrderDetailEntity> orderDetailEntities = orderDetailService.list(new LambdaQueryWrapper<OrderDetailEntity>()
                .eq(OrderDetailEntity::getOrderid, orderid));

        // 获取全部商品id
        List<String> goodsIds = orderDetailEntities.stream().map(OrderDetailEntity::getGoodsid).collect(Collectors.toList());

        // 查询出全部的商品实体类
        List<GoodsEntity> goodsEntityList = goodsService.listByIds(goodsIds);

        // 构造数据返回前端
        List<OrderGoodsVo> orderGoodsVoList = orderDetailEntities.stream().map(orderDetailEntity -> {
            OrderGoodsVo orderGoodsVo = new OrderGoodsVo();
            List<GoodsEntity> goodsEntities = goodsEntityList.stream()
                    .filter(goodsEntity
                            -> goodsEntity.getGoodsid().equals(orderDetailEntity.getGoodsid()))
                    .collect(Collectors.toList());
            orderGoodsVo.setGoodsName(goodsEntities.get(0).getGoodsname());
            orderGoodsVo.setHasInputSource(!StringUtils.isBlank(orderDetailEntity.getSupplierid()));
            orderGoodsVo.setOrderdetailid(orderDetailEntity.getOrderdetailid());

            return orderGoodsVo;
        }).collect(Collectors.toList());

        //
        return orderGoodsVoList;
    }

    /**
     * 批量订单出库
     * @param orderIds
     * @param token
     */
    @Override
    public void orderListOut(List<String> orderIds, String token) throws NoInputSourceException {

        // 根据order id查询所有orderDetail
        List<OrderDetailEntity> orderDetailEntities = orderDetailService.list(new LambdaQueryWrapper<OrderDetailEntity>()
                .in(OrderDetailEntity::getOrderid, orderIds));
        ArrayList<String> noInputNameList = new ArrayList<>();
        // 检查订单详情中是否已填报来源
        boolean isInput = true;
        for (OrderDetailEntity orderDetailEntity : orderDetailEntities) {
            if (StringUtils.isBlank(orderDetailEntity.getSupplierid())) {
                isInput = false;
                GoodsEntity goods = goodsService.getById(orderDetailEntity.getGoodsid());
                noInputNameList.add(goods.getGoodsname());
            }
        }
        // 如果发现为填写来源则抛出未填写来源异常
        if (!isInput) {
            throw new NoInputSourceException("订单出库异常", noInputNameList);
        } else {
            // 更新订单状态为已发货
            List<OrderEntity> orderEntities = orderIds.stream().map(orderId -> {
                OrderEntity orderEntity = new OrderEntity();
                orderEntity.setOrderid(orderId);
                orderEntity.setStatus(OrderConstant.SEND.getCode() + "");
                return orderEntity;
            }).collect(Collectors.toList());

            this.updateBatchById(orderEntities);
        }
    }

    /**
     * 获取账号下的各类订单状态
     *
     * @param token
     * @return
     */
    @Override
    public Map<String, Long> orderCountByToken(String token) {
        String userId = jwtHelper.getUserId(token);

        PurchaserEntity purchaserEntity = purchaserService.getOne(new LambdaQueryWrapper<PurchaserEntity>()
                .eq(PurchaserEntity::getUserId, userId));

        String distributionId = distributionDao.getDistributionIdByUserId(userId);
        long unAccept = 0;
        long unSend = 0;
        long send = 0;
        long receive = 0;
        long finish = 0;
        long refuseReceive = 0;
        long refuseAccept = 0;
        long cancel = 0;


        if (purchaserEntity != null) {

            unAccept = this.count(new LambdaQueryWrapper<OrderEntity>()
                    .eq(OrderEntity::getPurchaserid, purchaserEntity.getPurchaserid())
                    .eq(OrderEntity::getStatus, "0"));
            unSend = this.count(new LambdaQueryWrapper<OrderEntity>()
                    .eq(OrderEntity::getPurchaserid, purchaserEntity.getPurchaserid()).eq(OrderEntity::getStatus, "1"));
            send = this.count(new LambdaQueryWrapper<OrderEntity>()
                    .eq(OrderEntity::getPurchaserid, purchaserEntity.getPurchaserid()).eq(OrderEntity::getStatus, "2"));
            receive = this.count(new LambdaQueryWrapper<OrderEntity>()
                    .eq(OrderEntity::getPurchaserid, purchaserEntity.getPurchaserid()).eq(OrderEntity::getStatus, "3"));
            finish = this.count(new LambdaQueryWrapper<OrderEntity>()
                    .eq(OrderEntity::getPurchaserid, purchaserEntity.getPurchaserid()).eq(OrderEntity::getStatus, "4"));
            refuseReceive = this.count(new LambdaQueryWrapper<OrderEntity>()
                    .eq(OrderEntity::getPurchaserid, purchaserEntity.getPurchaserid()).eq(OrderEntity::getStatus, "5"));
            refuseAccept = this.count(new LambdaQueryWrapper<OrderEntity>()
                    .eq(OrderEntity::getPurchaserid, purchaserEntity.getPurchaserid()).eq(OrderEntity::getStatus, "6"));
            cancel = this.count(new LambdaQueryWrapper<OrderEntity>()
                    .eq(OrderEntity::getPurchaserid, purchaserEntity.getPurchaserid()).eq(OrderEntity::getStatus, "7"));
        }

        if (distributionId != null) {
            unAccept = this.count(new LambdaQueryWrapper<OrderEntity>()
                    .eq(OrderEntity::getDistributionid, distributionId)
                    .eq(OrderEntity::getStatus, "0"));
            unSend = this.count(new LambdaQueryWrapper<OrderEntity>()
                    .eq(OrderEntity::getDistributionid, distributionId).eq(OrderEntity::getStatus, "1"));
            send = this.count(new LambdaQueryWrapper<OrderEntity>()
                    .eq(OrderEntity::getDistributionid, distributionId).eq(OrderEntity::getStatus, "2"));
            receive = this.count(new LambdaQueryWrapper<OrderEntity>()
                    .eq(OrderEntity::getDistributionid, distributionId).eq(OrderEntity::getStatus, "3"));
            finish = this.count(new LambdaQueryWrapper<OrderEntity>()
                    .eq(OrderEntity::getDistributionid, distributionId).eq(OrderEntity::getStatus, "4"));
            refuseReceive = this.count(new LambdaQueryWrapper<OrderEntity>()
                    .eq(OrderEntity::getDistributionid, distributionId).eq(OrderEntity::getStatus, "5"));
            refuseAccept = this.count(new LambdaQueryWrapper<OrderEntity>()
                    .eq(OrderEntity::getDistributionid, distributionId).eq(OrderEntity::getStatus, "6"));
            cancel = this.count(new LambdaQueryWrapper<OrderEntity>()
                    .eq(OrderEntity::getDistributionid, distributionId).eq(OrderEntity::getStatus, "7"));

        }


        long total = 0;
        HashMap<String, Long> result = new HashMap<>();
        result.put("unAccept", unAccept);
        result.put("unSend", unSend);
        result.put("send", send);
        result.put("receive", receive);
        result.put("finish", finish);
        result.put("refuseReceive", refuseReceive);
        result.put("refuseAccept", refuseAccept);
        result.put("cancel", cancel);


        for (Map.Entry<String, Long> item : result.entrySet()) {
            total += item.getValue().intValue();
        }

        result.put("total", total);

        return result;

    }


    /**
     * 结账
     *
     * @param orderCheckOutVoList
     * @param token
     */
    @Transactional
    @Override
    public String checkOut(OrderCheckOutVo orderCheckOutVoList, String token) {
        String purchaserId = purchaserService.getPurchaserIdByToken(token);

        String distributionId = orderCheckOutVoList.getDistributionId();

        if (!StringUtils.isBlank(purchaserId) && !StringUtils.isBlank(distributionId)) {

            List<OrderCheckOutVo.Goods> goodsList = orderCheckOutVoList.getGoodsList();
            List<String> goodsIds = goodsList.stream().map(OrderCheckOutVo.Goods::getGoodsid).collect(Collectors.toList());
            List<GoodsEntity> goodsEntities = goodsService.listByIds(goodsIds);

            if (goodsList.size() > 0) {
                OrderEntity orderEntity = new OrderEntity();
                orderEntity.setOrderno(UUID.randomUUID().toString().replace("-", ""));

                // 计算总价
                BigDecimal totalMoney = new BigDecimal("0");
                for (OrderCheckOutVo.Goods goods : goodsList) {
                    // 找出对应的实体类
                    List<GoodsEntity> goodsEntityList = goodsEntities.stream()
                            .filter(goodsEntity -> goodsEntity.getGoodsid().equals(goods.getGoodsid()))
                            .collect(Collectors.toList());
                    if (goodsEntityList.size() > 0) {
                        // 获取价格
                        BigDecimal price = goodsEntityList.get(0).getPrice();

                        // 获取数量
                        BigDecimal number = new BigDecimal(goods.getNumber());


                        // 计算总价
                        totalMoney = totalMoney.add(number.multiply(price));
                    }
                }
                orderEntity.setDistributiondate(orderCheckOutVoList.getDistributiondate());
                orderEntity.setMoney(totalMoney);

                // 获取地址 设置地址信息
                AddressEntity addressEntity = addressService.getById(orderCheckOutVoList.getAddressId());
                orderEntity.setAddress(addressEntity.getAddress());
                orderEntity.setAddrid(orderCheckOutVoList.getAddressId());
                orderEntity.setAddrName(addressEntity.getName());
                orderEntity.setAddrPhone(addressEntity.getPhone());
                orderEntity.setAddrArea(addressEntity.getArea());


                String userId = jwtHelper.getUserId(token);

                orderEntity.setAdduser(userId);
                orderEntity.setTimerange(orderCheckOutVoList.getTimerange());
                orderEntity.setStatus(OrderConstant.UN_ACCEPT.getCode() + "");
                orderEntity.setDistributionid(distributionId);
                orderEntity.setPurchaserid(purchaserId);
                orderEntity.setOrderid(UUID.randomUUID().toString());
                boolean saveResult = this.save(orderEntity);

                // 如果订单保存成功，则保存订单详情
                if (saveResult) {
                    List<OrderDetailEntity> orderDetailEntities = goodsList.stream().map(goods -> {
                        OrderDetailEntity orderDetailEntity = null;
                        // 找出对应的实体类
                        List<GoodsEntity> goodsEntityList = goodsEntities.stream()
                                .filter(goodsEntity -> goodsEntity.getGoodsid().equals(goods.getGoodsid()))
                                .collect(Collectors.toList());
                        if (goodsEntityList.size() > 0) {
                            GoodsEntity goodsEntity = goodsEntityList.get(0);
                            orderDetailEntity = new OrderDetailEntity();
                            orderDetailEntity.setAmount(new BigDecimal(goods.getNumber()));
                            orderDetailEntity.setMessage(orderCheckOutVoList.getMessage());
                            orderDetailEntity.setOrderid(orderEntity.getOrderid());
                            orderDetailEntity.setClassificationid(goodsEntity.getClassificationid());
                            ClassificationEntity classificationEntity = classificationService.getById(goodsEntity.getClassificationid());
                            orderDetailEntity.setClassificationname(classificationEntity.getClassificationname());
                            orderDetailEntity.setPrice(goodsEntity.getPrice());
                            orderDetailEntity.setGoodsunit(goodsEntity.getGoodsunit());
                            orderDetailEntity.setImgurl(goodsEntity.getGoodsimg());
                            orderDetailEntity.setGoodsid(goodsEntity.getGoodsid());
                            orderDetailEntity.setOrderdetailid(UUID.randomUUID().toString());
                        }

                        return orderDetailEntity;
                    }).collect(Collectors.toList());

                    // 保存订单详情
                    boolean saveOrderDetailResult = orderDetailService.saveBatch(orderDetailEntities);
                    if (!saveOrderDetailResult) {
                        throw new RuntimeException("订单详情保存失败");
                    } else {
                        // 清空购物车
                        shoppingcarDao.delete(new LambdaQueryWrapper<ShoppingcarEntity>()
                                .eq(ShoppingcarEntity::getDistributionId, distributionId)
                                .eq(ShoppingcarEntity::getPurchaserid, purchaserId));
                        return orderEntity.getOrderno();
                    }
                } else {
                    throw new RuntimeException("订单保存失败");
                }
            }

        }

        return null;
    }

    /**
     * 当前配送商的订单列表
     *
     * @param params
     * @param token
     * @return
     */
    @Override
    public PageUtils purchaserList(Map<String, Object> params, String token) {
        String status = (String) params.get("status");
        String purchaserId = purchaserService.getPurchaserIdByToken(token);
        LambdaQueryWrapper<OrderEntity> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isBlank(status)) {
            wrapper.eq(OrderEntity::getStatus, status);
        }
        wrapper.eq(OrderEntity::getPurchaserid, purchaserId);
        // 查询当前用户的订单
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                wrapper
        );

        PageUtils pageUtils = new PageUtils(page);

        // 获取全部订单id
        List<OrderEntity> records = page.getRecords();
        List<String> orderIds = records.stream().map(OrderEntity::getOrderid).collect(Collectors.toList());

        // 获取所有配送商id
        List<String> distributionIds = records.stream().map(OrderEntity::getDistributionid).collect(Collectors.toList());

        List<OrderDetailEntity> orderDetailEntities = null;
        if (orderIds.size() > 0) {
            // 获取所有订单详情
            orderDetailEntities = orderDetailService.list(new LambdaQueryWrapper<OrderDetailEntity>()
                    .in(OrderDetailEntity::getOrderid, orderIds));
        }
        List<GoodsEntity> goodsEntities = null;
        // 获取所有商品详情
        if (orderDetailEntities != null && orderDetailEntities.size() > 0) {
            List<String> goodsIds = orderDetailEntities.stream().map(OrderDetailEntity::getGoodsid).collect(Collectors.toList());
            goodsEntities = goodsService.listByIds(goodsIds);
        }

        // 获取所有配送商实体
        List<DistributionEntity> distributionEntities = null;
        if (distributionIds.size() > 0) {
            distributionEntities = distributionDao.selectBatchIds(distributionIds);
        }

        List<DistributionEntity> distributionEntityList = distributionEntities;
        List<OrderDetailEntity> orderDetailEntityList = orderDetailEntities;
        List<GoodsEntity> goodsEntityList = goodsEntities;
        // 构造vo返回前端
        List<PurchaserOrderResVo> purchaserOrderResVos = records.stream().map(orderEntity -> {
            PurchaserOrderResVo purchaserOrderResVo = new PurchaserOrderResVo();
            BeanUtils.copyProperties(orderEntity, purchaserOrderResVo);

            // 获取配送商公司名
            if (distributionEntityList != null) {
                List<DistributionEntity> distribution = distributionEntityList.stream().filter(distributionEntity
                                -> distributionEntity.getDistributionid().equals(orderEntity.getDistributionid()))
                        .collect(Collectors.toList());
                if (distribution.size() > 0)
                    purchaserOrderResVo.setDistributionName(distribution.get(0).getCompanyname());
            }

            // 获取当前订单的商品详情
            List<OrderDetailEntity> detailEntities = orderDetailEntityList.stream().filter(orderDetailEntity
                            -> orderDetailEntity.getOrderid().equals(orderEntity.getOrderid()))
                    .collect(Collectors.toList());

            // 构造商品详情
            List<PurchaserOrderResVo.OrderDetailVo> orderDetailVos = null;
            if (detailEntities.size() > 0) {
                orderDetailVos = detailEntities.stream().map(orderDetailEntity -> {
                    PurchaserOrderResVo.OrderDetailVo orderDetailVo = new PurchaserOrderResVo.OrderDetailVo();
                    BeanUtils.copyProperties(orderDetailEntity, orderDetailVo);
                    orderDetailVo.setNumber(orderDetailEntity.getAmount().intValue());
                    // 设置商品属性
                    if (goodsEntityList != null) {
                        List<GoodsEntity> entityList = goodsEntityList.stream().filter(goodsEntity
                                        -> goodsEntity.getGoodsid().equals(orderDetailEntity.getGoodsid()))
                                .collect(Collectors.toList());
                        if (entityList.size() > 0) {
                            BeanUtils.copyProperties(entityList.get(0), orderDetailVo);
                            orderDetailVo.setGoodsName(entityList.get(0).getGoodsname());
                        }
                    }

                    return orderDetailVo;
                }).collect(Collectors.toList());
            }
            purchaserOrderResVo.setChildren(orderDetailVos);


            return purchaserOrderResVo;
        }).collect(Collectors.toList());

        pageUtils.setList(purchaserOrderResVos);
        return pageUtils;
    }

    /**
     * 订单签收
     * @param orderReceiveVo
     * @param token
     */
    @Transactional
    @Override
    public void orderReceive(OrderReceiveVo orderReceiveVo, String token) {
        String userId = jwtHelper.getUserId(token);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderid(orderReceiveVo.getOrderid());
        orderEntity.setSignuser(userId);
        orderEntity.setSigntime(new Date());
        orderEntity.setStatus(OrderConstant.RECEIVE.getCode() + "");
        this.updateById(orderEntity);
        List<OrderReceiveVo.OrderDetail> children = orderReceiveVo.getChildren();
        List<OrderDetailEntity> orderDetailEntities = children.stream().map(orderDetail -> {
            OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
            orderDetailEntity.setAmount(orderDetail.getActualamount());
            orderDetailEntity.setOrderdetailid(orderDetail.getOrderdetailid());
            return orderDetailEntity;

        }).collect(Collectors.toList());
        orderDetailService.updateBatchById(orderDetailEntities);

    }

    /**
     * 订单完成
     * @param orderid
     * @param token
     */
    @Override
    public void orderFinish(String orderid, String token) {
        String userId = jwtHelper.getUserId(token);
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStatus(OrderConstant.FINISH.getCode() + "");
        orderEntity.setFinishtime(new Date());
        orderEntity.setOrderid(orderid);
        orderEntity.setFinishuser(userId);
        this.updateById(orderEntity);
    }

}
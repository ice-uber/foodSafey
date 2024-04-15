package com.weike.foodsafe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.weike.common.constant.OrderConstant;
import com.weike.common.exception.NoInputSourceException;
import com.weike.common.utils.JwtHelper;
import com.weike.foodsafe.dao.ShoppingcarDao;
import com.weike.foodsafe.entity.*;
import com.weike.foodsafe.service.*;
import com.weike.foodsafe.vo.GoodsSourceVo;
import com.weike.foodsafe.vo.OrderGoodsVo;
import com.weike.foodsafe.vo.OrderVo;
import com.weike.foodsafe.vo.order.OrderCheckOutVo;
import com.weike.system.entity.UserEntity;
import com.weike.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    private DistributionService distributionService;

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
     * @param params
     * @return
     */
    @Override
    public PageUtils queryOrderPage(Map<String, Object> params , String token) {
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
            wrapper.like(OrderEntity :: getOrderno , orderNo);
        }
        if (!StringUtils.isBlank(startDate)) {
            wrapper.ge(OrderEntity :: getAddtime , startDate);
        }
        if (!StringUtils.isBlank(endDate)) {
            wrapper.le(OrderEntity :: getAddtime , endDate);
        }
        if (!StringUtils.isBlank(purchaserName)) {
            PurchaserEntity purchaserEntity = purchaserService.getOne(new LambdaQueryWrapper<PurchaserEntity>()
                    .eq(PurchaserEntity::getCompanyname, purchaserName));
            if (purchaserEntity != null) {
                wrapper.le(OrderEntity :: getPurchaserid , purchaserEntity.getPurchaserid());
            } else wrapper.le(OrderEntity :: getPurchaserid , -1);

        }

        if (!StringUtils.isBlank(status)) {
            // 当status为-1时代表查询全部
            if (!status.equals("-1") ) {
                wrapper.eq(OrderEntity :: getStatus , status);
            }
        }

        // 查询当前账号属于哪个配送商
        DistributionEntity distributionEntity = distributionService.getOne(new LambdaQueryWrapper<DistributionEntity>()
                .eq(DistributionEntity::getUserId, userId));

        wrapper.eq(OrderEntity :: getDistributionid , distributionEntity.getDistributionid());

        // 返回的结果
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                wrapper
        );
        List<OrderEntity> pageRecords = page.getRecords();

        List<OrderVo> orderVoList = null;

        if (pageRecords.size() > 0) {
            // 收集订单id
            List<String> orderIds = pageRecords.stream().map(OrderEntity::getOrderid).collect(Collectors.toList());

            // 收集采购商id
            List<String> purchaserIds = pageRecords.stream().map(OrderEntity::getPurchaserid).collect(Collectors.toList());

            // 查出所需的全部采购商实体
            List<PurchaserEntity> purchaserEntityList = purchaserService.listByIds(purchaserIds);

            // 查出当前所有的订单详情
            List<OrderDetailEntity> orderDetailEntityList = orderDetailService.list(new LambdaQueryWrapper<OrderDetailEntity>()
                    .in(OrderDetailEntity::getOrderid, orderIds));

            // 查出所需的所有商品详情id
            List<String> goodsIds = orderDetailEntityList.stream().map(OrderDetailEntity::getGoodsid).collect(Collectors.toList());

            // 查出所有的商品信息
            List<GoodsEntity> goodsEntityList = goodsService.listByIds(goodsIds);


            // 构造Vo
            orderVoList = pageRecords.stream().map(orderEntity -> {
                OrderVo orderVo = new OrderVo();
                BeanUtils.copyProperties(orderEntity, orderVo);

                // 查询当前订单的采购商实体
                List<PurchaserEntity> purchaserEntities = purchaserEntityList.stream().filter(purchaserEntity
                                -> purchaserEntity.getPurchaserid().equals(orderEntity.getPurchaserid()))
                        .collect(Collectors.toList());
                orderVo.setPurchaserName(purchaserEntities.get(0).getCompanyname());

                // 查询当前订单的订单详情
                List<OrderDetailEntity> orderDetailEntities = orderDetailEntityList.stream().filter(orderDetailEntity
                                -> orderDetailEntity.getOrderid().equals(orderEntity.getOrderid()))
                        .collect(Collectors.toList());

                // 构造订单详情
                List<OrderVo.OrderDetailVo> orderDetailVos = orderDetailEntities.stream().map(orderDetailEntity -> {
                    OrderVo.OrderDetailVo orderDetailVo = new OrderVo.OrderDetailVo();
                    BeanUtils.copyProperties(orderDetailEntity, orderDetailVo);
                    // 设置不同字段

                    // 过滤出需要的商品实体类
                    List<GoodsEntity> goodsEntities = goodsEntityList.stream()
                            .filter(goodsEntity -> goodsEntity.getGoodsid().equals(orderDetailEntity.getGoodsid()))
                            .collect(Collectors.toList());

                    orderDetailVo.setGoodsName(goodsEntities.get(0).getGoodsname());
                    return orderDetailVo;
                }).collect(Collectors.toList());

                orderVo.setChildren(orderDetailVos);
                return orderVo;
            }).collect(Collectors.toList());
        }


        // 设置返回结果
        PageUtils pageUtils = new PageUtils(page);
        pageUtils.setList(orderVoList);
        return pageUtils;
    }

    /**
     * 受理/拒绝订单
     * @param orderid
     */
    @Override
    public void acceptOrder(String orderid,String status  , String token) {
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
     * @param orderid
     * @param token
     */
    @Override
    public void orderOut(String orderid, String token) throws NoInputSourceException {

        OrderEntity orderEntity = this.getById(orderid);

        if (orderEntity!= null) {
            // 获取全部订单详情实体类
            List<OrderDetailEntity> orderDetailEntities = orderDetailService.list(
                    new LambdaQueryWrapper<OrderDetailEntity>()
                            .eq(OrderDetailEntity::getOrderid, orderEntity.getOrderid())
            );

            ArrayList<String> noInputNameList = new ArrayList<>();
            // 检查订单详情中是否已填报来源
            boolean isInput = true;
            for(OrderDetailEntity orderDetailEntity : orderDetailEntities ) {
                System.out.println(orderDetailEntity.getSupplierid());
                if (StringUtils.isBlank(orderDetailEntity.getSupplierid())) {
                    isInput = false;
                    GoodsEntity goods = goodsService.getById(orderDetailEntity.getGoodsid());
                    noInputNameList.add(goods.getGoodsname());
                }
            }
            // 如果发现为填写来源则抛出未填写来源异常
            if (!isInput) {
                throw new NoInputSourceException("订单出库异常" , noInputNameList);
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

        System.out.println(orderDetailEntityList);
        // 批量更新
        orderDetailService.updateBatchById(orderDetailEntityList);
    }


    /**
     * 获取订单order下的全商品vo
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
     *
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
        for(OrderDetailEntity orderDetailEntity : orderDetailEntities ) {
            if (StringUtils.isBlank(orderDetailEntity.getSupplierid())) {
                isInput = false;
                GoodsEntity goods = goodsService.getById(orderDetailEntity.getGoodsid());
                noInputNameList.add(goods.getGoodsname());
            }
        }
        // 如果发现为填写来源则抛出未填写来源异常
        if (!isInput) {
            throw new NoInputSourceException("订单出库异常" , noInputNameList);
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
     * @param token
     * @return
     */
    @Override
    public Map<String, Integer> orderCountByToken(String token) {
        String distributionId = distributionService.getDistributionIdByToken(token);

        List<OrderEntity> orderEntities = this.list(new LambdaQueryWrapper<OrderEntity>()
                .eq(OrderEntity::getDistributionid, distributionId));

        int unAccept = 0;
        int unSend = 0;
        int send = 0;
        int receive = 0;
        int finish = 0;
        for (OrderEntity orderEntity : orderEntities) {
            switch (orderEntity.getStatus()){
                case "0": unAccept += 1; break;
                case "1": unSend += 1; break;
                case "2": send += 1; break;
                case "3": receive += 1; break;
                case "4": finish += 1; break;
            }
        }
        HashMap<String, Integer> result = new HashMap<>();
        result.put("unAccept" , unAccept);
        result.put("unSend" , unSend);
        result.put("send" , send);
        result.put("receive" , receive);
        result.put("finish" , finish);

        return result;
    }

    /**
     * 结账
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
                orderEntity.setOrderno(UUID.randomUUID().toString().replace("-" , ""));

                // 计算总价
                BigDecimal totalMoney = new BigDecimal("0");
                for(OrderCheckOutVo.Goods goods : goodsList) {
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
                            orderDetailEntity.setImgurl(goodsEntity.getImgurl());
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
                                .eq(ShoppingcarEntity :: getDistributionId , distributionId)
                                .eq(ShoppingcarEntity :: getPurchaserid , purchaserId));
                        return orderEntity.getOrderno();
                    }



                } else {
                    throw new RuntimeException("订单保存失败");
                }
            }

        }

        return null;
    }

}
package com.weike.foodsafe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.weike.common.constant.OrderConstant;
import com.weike.foodsafe.dao.OrderDao;
import com.weike.foodsafe.entity.GoodsEntity;
import com.weike.foodsafe.entity.OrderEntity;
import com.weike.foodsafe.entity.PurchaserEntity;
import com.weike.foodsafe.service.*;
import com.weike.foodsafe.vo.OrderDetailVo;
import com.weike.foodsafe.vo.OrderVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.Query;

import com.weike.foodsafe.dao.OrderDetailDao;
import com.weike.foodsafe.entity.OrderDetailEntity;


@Service("orderDetailService")
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailDao, OrderDetailEntity> implements OrderDetailService {

    @Autowired
    private DistributionService distributionService;
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private PurchaserService purchaserService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderDetailEntity> page = this.page(
                new Query<OrderDetailEntity>().getPage(params),
                new QueryWrapper<OrderDetailEntity>()
        );

        return new PageUtils(page);
    }

    /**
     *  获取当前配送商下的订单详情列表
     * @param params
     * @return
     */
    @Override
    public PageUtils queryOrderDetailPage(Map<String, Object> params , String token) {
        String distributionId = distributionService.getDistributionIdByToken(token);

        String purchaserid = (String) params.get("purchaserid");
        String endDate = (String) params.get("endDate");
        String startDate = (String) params.get("startDate");

        LambdaQueryWrapper<OrderEntity> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(OrderEntity :: getDistributionid , distributionId);
        wrapper.eq(OrderEntity :: getStatus , OrderConstant.FINISH.getCode());

        if (!StringUtils.isBlank(purchaserid)) {
            wrapper.eq(OrderEntity :: getPurchaserid , purchaserid);
        }

        if (!StringUtils.isBlank(startDate)) {
            wrapper.ge(OrderEntity :: getAddtime , startDate);
        }
        if (!StringUtils.isBlank(endDate)) {
            wrapper.le(OrderEntity :: getAddtime , endDate);
        }

        // 查询当前账号下的订单
        List<OrderEntity> orderEntities = orderDao.selectList(wrapper);
        PageUtils pageUtils = new PageUtils(null , 0 , 0 , 0);
        if (orderEntities != null && orderEntities.size() > 0){
            // 获取获取所有订单id
            List<String> orderIds = orderEntities.stream().map(OrderEntity::getOrderid).collect(Collectors.toList());

            IPage<OrderDetailEntity> page = this.page(
                    new Query<OrderDetailEntity>().getPage(params),
                    new LambdaQueryWrapper<OrderDetailEntity>()
                            .in(OrderDetailEntity::getOrderid, orderIds)
            );
            pageUtils = new PageUtils(page);

            // 获取所有商品id
            List<String> goodsIds = page.getRecords().stream().map(OrderDetailEntity::getGoodsid).collect(Collectors.toList());

            // 找出所有的商品名信息
            List<GoodsEntity> goodsEntities = goodsService.listByIds(goodsIds);

            // 获取所有的采购商id
            List<String> purchaserIds = orderEntities.stream().map(OrderEntity::getPurchaserid).collect(Collectors.toList());

            // 获取所有的采购商
            List<PurchaserEntity> purchaserEntities = purchaserService.listByIds(purchaserIds);

            List<OrderDetailVo> orderDetailVos = page.getRecords().stream().map(orderDetailEntity -> {
                OrderDetailVo orderDetailVo = new OrderDetailVo();
                BeanUtils.copyProperties(orderDetailEntity, orderDetailVo);
                // 找出订单编号 设置
                List<OrderEntity> entityList = orderEntities.stream()
                        .filter(orderEntity -> orderEntity.getOrderid().equals(orderDetailEntity.getOrderid()))
                        .collect(Collectors.toList());
                orderDetailVo.setOrderno(entityList.get(0).getOrderno());
                // 找出商品 设置商品名
                List<GoodsEntity> goodsEntityList = goodsEntities.stream()
                        .filter(goodsEntity -> goodsEntity.getGoodsid().equals(orderDetailEntity.getGoodsid()))
                        .collect(Collectors.toList());
                orderDetailVo.setGoodsName(goodsEntityList.get(0).getGoodsname());

                // 找出采购商 设置公司名
                List<PurchaserEntity> purchaserEntityList = purchaserEntities.stream()
                        .filter(purchaserEntity -> purchaserEntity.getPurchaserid().equals(entityList.get(0).getPurchaserid()))
                        .collect(Collectors.toList());
                orderDetailVo.setPurchaserName(purchaserEntityList.get(0).getCompanyname());


                // 设置总价
                if (orderDetailEntity.getActualamount() != null) {
                    orderDetailVo.setTotalMoney(orderDetailEntity.getPrice()
                            .multiply(orderDetailEntity.getActualamount()));
                } else {
                    orderDetailVo.setTotalMoney(orderDetailEntity.getPrice()
                            .multiply(orderDetailEntity.getAmount()));
                }

                // 设置签收时间
                orderDetailVo.setSigntime(entityList.get(0).getSigntime());

                return orderDetailVo;
            }).collect(Collectors.toList());
            pageUtils.setList(orderDetailVos);
        }

        return pageUtils;
    }

    /**
     * 获取账号下的总金额及总数
     * @param token
     * @return
     */
    @Override
    public Map<String, Object> total(String token) {
        String distributionId = distributionService.getDistributionIdByToken(token);

        // 获取账号下的所有订单
        List<OrderEntity> orderEntities = orderDao.selectList(new LambdaQueryWrapper<OrderEntity>()
                .eq(OrderEntity::getDistributionid, distributionId)
                .eq(OrderEntity :: getStatus , OrderConstant.FINISH.getCode()));

        List<String> orderIds = orderEntities.stream().map(OrderEntity::getOrderid).collect(Collectors.toList());

        HashMap<String, Object> result = new HashMap<>();
        if (orderIds.size() > 0) {
            List<OrderDetailEntity> orderDetailEntities = this.list(new LambdaQueryWrapper<OrderDetailEntity>()
                    .in(OrderDetailEntity::getOrderid, orderIds));

            BigDecimal total = new BigDecimal("0");
            for (OrderDetailEntity orderDetailEntity : orderDetailEntities) {
                if (orderDetailEntity.getActualamount() != null){
                    total = total.add(orderDetailEntity.getPrice().multiply(orderDetailEntity.getActualamount()));
                } else {
                    total = total.add(orderDetailEntity.getPrice().multiply(orderDetailEntity.getAmount()));
                }

            }


            result.put("totalMoney" , total);
            result.put("totalNumber" , orderDetailEntities.size());
        }

        return result;
    }

}
package com.weike.foodsafe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weike.common.exception.NoInputSourceException;
import com.weike.common.utils.PageUtils;
import com.weike.foodsafe.entity.OrderEntity;
import com.weike.foodsafe.vo.GoodsSourceVo;
import com.weike.foodsafe.vo.OrderGoodsVo;
import com.weike.foodsafe.vo.order.OrderCheckOutVo;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryOrderPage(Map<String, Object> params , String token);

    void acceptOrder(String orderid ,String status , String token);

    void orderOut(String orderid, String token) throws NoInputSourceException;

    void orderGoodsSource(GoodsSourceVo goodsSourceVo);

    List<OrderGoodsVo> getGoodsByOrderId(String orderid);

    void orderListOut(List<String> orderIds, String token) throws NoInputSourceException;

    Map<String, Integer> orderCountByToken(String token);

    String checkOut(OrderCheckOutVo orderCheckOutVoList , String token);

}


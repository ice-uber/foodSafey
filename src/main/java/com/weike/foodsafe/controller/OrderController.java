package com.weike.foodsafe.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.weike.common.exception.NoInputSourceException;
import com.weike.foodsafe.vo.GoodsSourceVo;
import com.weike.foodsafe.vo.OrderGoodsVo;
import com.weike.foodsafe.vo.order.OrderCheckOutVo;
import com.weike.foodsafe.vo.order.OrderReceiveVo;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.weike.foodsafe.entity.OrderEntity;
import com.weike.foodsafe.service.OrderService;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.R;



/**
 * 
 *
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
@RestController
@Slf4j
@RequestMapping("foodsafe/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 订单完成
     * @param orderid
     * @return
     */
    @PostMapping("/finish/{orderid}")
    public R orderReceive(@PathVariable("orderid") String orderid ,
                          @RequestHeader String token) {
        orderService.orderFinish(orderid , token);
        return R.ok();
    }

    /**
     * 订单签收
     * @param orderReceiveVo
     * @return
     */
    @PostMapping("/receive")
    public R orderReceive(@RequestBody OrderReceiveVo orderReceiveVo ,
                      @RequestHeader String token) {
        orderService.orderReceive(orderReceiveVo , token);
        return R.ok();
    }


    /**
     * 结账
     * @param orderCheckOutVoList
     * @return
     */
    @PostMapping("/checkout")
    public R checkOut(@RequestBody OrderCheckOutVo orderCheckOutVoList ,
                      @RequestHeader String token) {
        String orderNo = orderService.checkOut(orderCheckOutVoList , token);
        return R.ok().put("data" , orderNo);
    }


    /**
     * 获取各类订单状态统计
     * @param token
     * @return
     */
    @GetMapping("/count")
    public R ordercount(@RequestHeader String token) {
        Map<String , Long> goodsVoList = orderService.orderCountByToken(token);
        return R.ok().put("data" , goodsVoList);
    }

    /**
     * 订单商品来源
     * @param goodsSourceVo
     * @return
     */
    @PostMapping("/source")
    public R goodsSource(@RequestBody GoodsSourceVo goodsSourceVo) {
        orderService.orderGoodsSource(goodsSourceVo);
        return R.ok();
    }


    /**
     * 获取订单下的商品详情
     * @param orderid
     * @return
     */
    @GetMapping("/goods/{orderid}")
    public R orderOut(@PathVariable("orderid") String orderid) {
        List<OrderGoodsVo> goodsVoList = orderService.getGoodsByOrderId(orderid);
        return R.ok().put("data" , goodsVoList);
    }

    /**
     * 批量订单出库
     * @param
     * @param token
     * @return
     */
    @PostMapping("/out/list")
    public R orderOut(@RequestBody List<String> orderIds,
                      @RequestHeader String token) throws NoInputSourceException {
        orderService.orderListOut(orderIds , token);
        return R.ok();
    }

    /**
     * 订单出库
     * @param orderid
     * @param token
     * @return
     */
    @GetMapping("/out/{orderid}")
    public R orderOut(@PathVariable("orderid") String orderid,
                         @RequestHeader String token) throws NoInputSourceException {
        orderService.orderOut(orderid , token);
        return R.ok();
    }

    /**
     * 受理/不受理订单
     * @param orderid
     * @return
     */
    @GetMapping("/accept/{orderid}/{status}")
    public R acceptOrder(@PathVariable("orderid") String orderid,
                         @PathVariable("status") String status,
                         @RequestHeader String token){
        orderService.acceptOrder(orderid , status , token);
        return R.ok();
    }

    /**
     * 获取当前采购商下的订单列表
     */
    @RequestMapping("/list/purchaser")
    public R purchaserList(@RequestParam Map<String, Object> params,
                  @RequestHeader String token){
        PageUtils page = orderService.purchaserList(params , token);
        return R.ok().put("data", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("foodsafe:order:list")
    public R list(@RequestParam Map<String, Object> params,
                  @RequestHeader String token){
        PageUtils page = orderService.queryOrderPage(params , token);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{orderid}")
    //@RequiresPermissions("foodsafe:order:info")
    public R info(@PathVariable("orderid") String orderid){
		OrderEntity order = orderService.getById(orderid);

        return R.ok().put("order", order);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("foodsafe:order:save")
    public R save(@RequestBody OrderEntity order){
		orderService.save(order);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("foodsafe:order:update")
    public R update(@RequestBody OrderEntity order){
		orderService.updateById(order);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("foodsafe:order:delete")
    public R delete(@RequestBody String[] orderids){
		orderService.removeByIds(Arrays.asList(orderids));

        return R.ok();
    }

}

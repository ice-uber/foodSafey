package com.weike.foodsafe.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.weike.foodsafe.entity.OrderDetailEntity;
import com.weike.foodsafe.service.OrderDetailService;
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
@RequestMapping("foodsafe/orderdetail")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * 统计账号订单总数及总金额
     */
    @RequestMapping("/total")
    public R total(@RequestHeader String token){
        Map<String , Object> total = orderDetailService.total(token);
        return R.ok().put("data", total);
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,
                  @RequestHeader String token){
        PageUtils page = orderDetailService.queryOrderDetailPage(params , token);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{orderdetailid}")
    //@RequiresPermissions("foodsafe:orderdetail:info")
    public R info(@PathVariable("orderdetailid") String orderdetailid){
		OrderDetailEntity orderDetail = orderDetailService.getById(orderdetailid);

        return R.ok().put("orderDetail", orderDetail);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("foodsafe:orderdetail:save")
    public R save(@RequestBody OrderDetailEntity orderDetail){
		orderDetailService.save(orderDetail);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("foodsafe:orderdetail:update")
    public R update(@RequestBody OrderDetailEntity orderDetail){
		orderDetailService.updateById(orderDetail);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("foodsafe:orderdetail:delete")
    public R delete(@RequestBody String[] orderdetailids){
		orderDetailService.removeByIds(Arrays.asList(orderdetailids));

        return R.ok();
    }

}

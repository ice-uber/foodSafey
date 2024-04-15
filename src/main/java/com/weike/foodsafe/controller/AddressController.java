package com.weike.foodsafe.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.weike.foodsafe.entity.AddressEntity;
import com.weike.foodsafe.service.AddressService;
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
@RequestMapping("foodsafe/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * 获取当前用户的全部地址
     */
    @RequestMapping("/list")
    //@RequiresPermissions("foodsafe:address:info")
    public R listAddress(@RequestHeader String token){
        List<AddressEntity> address = addressService.getBypurchaserId(token);
        return R.ok().put("data", address);
    }

    /**
     * 列表
     */
//    @RequestMapping("/list")
//    //@RequiresPermissions("foodsafe:address:list")
//    public R list(@RequestParam Map<String, Object> params){
//        PageUtils page = addressService.queryPage(params);
//
//        return R.ok().put("page", page);
//    }


    /**
     * 信息
     */
    @RequestMapping("/info/{addressid}")
    //@RequiresPermissions("foodsafe:address:info")
    public R info(@PathVariable("addressid") String addressid){
		AddressEntity address = addressService.getById(addressid);

        return R.ok().put("data", address);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("foodsafe:address:save")
    public R save(@RequestBody AddressEntity address){
		addressService.save(address);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("foodsafe:address:update")
    public R update(@RequestBody AddressEntity address){
		addressService.updateById(address);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("foodsafe:address:delete")
    public R delete(@RequestBody String[] addressids){
		addressService.removeByIds(Arrays.asList(addressids));

        return R.ok();
    }

}

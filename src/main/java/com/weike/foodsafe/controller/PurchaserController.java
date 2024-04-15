package com.weike.foodsafe.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weike.foodsafe.entity.PurchaserEntity;
import com.weike.foodsafe.service.PurchaserService;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.R;



/**
 * 采购商表
 *
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
@RestController
@RequestMapping("foodsafe/purchaser")
public class PurchaserController {
    @Autowired
    private PurchaserService purchaserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("foodsafe:purchaser:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = purchaserService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{purchaserid}")
    //@RequiresPermissions("foodsafe:purchaser:info")
    public R info(@PathVariable("purchaserid") String purchaserid){
		PurchaserEntity purchaser = purchaserService.getById(purchaserid);

        return R.ok().put("purchaser", purchaser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("foodsafe:purchaser:save")
    public R save(@RequestBody PurchaserEntity purchaser){
		purchaserService.save(purchaser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("foodsafe:purchaser:update")
    public R update(@RequestBody PurchaserEntity purchaser){
		purchaserService.updateById(purchaser);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("foodsafe:purchaser:delete")
    public R delete(@RequestBody String[] purchaserids){
		purchaserService.removeByIds(Arrays.asList(purchaserids));

        return R.ok();
    }

}

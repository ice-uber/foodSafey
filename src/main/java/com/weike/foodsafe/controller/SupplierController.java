package com.weike.foodsafe.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.weike.foodsafe.entity.SupplierEntity;
import com.weike.foodsafe.service.SupplierService;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.R;



/**
 * 供货商表
 *
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
@RestController
@RequestMapping("foodsafe/supplier")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    /**
     * 获取当前配送商下的全部供货商信息
     */
    @RequestMapping("/list")
    public R list(@RequestHeader String token){
        List<SupplierEntity> supplierEntities = supplierService.getSupplierByToken(token);
        return R.ok().put("data", supplierEntities);
    }

    /**
     * 获取当前配送商下的全部供货商分页信息
     */
    @RequestMapping("/list/page")
    public R listPage(
            @RequestParam Map<String , Object> params,
            @RequestHeader String token){
        PageUtils page = supplierService.getSupplierPageByToken(params ,token);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{supplierid}")
    //@RequiresPermissions("foodsafe:supplier:info")
    public R info(@PathVariable("supplierid") String supplierid){
		SupplierEntity supplier = supplierService.getById(supplierid);

        return R.ok().put("supplier", supplier);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("foodsafe:supplier:save")
    public R save(@RequestBody SupplierEntity supplier,
                  @RequestHeader String token){
		supplierService.saveSupplier(supplier , token);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("foodsafe:supplier:update")
    public R update(@RequestBody SupplierEntity supplier){
		supplierService.updateById(supplier);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("foodsafe:supplier:delete")
    public R delete(@RequestBody String[] supplierids){
		supplierService.removeByIds(Arrays.asList(supplierids));

        return R.ok();
    }

}

package com.weike.system.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weike.system.entity.RolePermissionDevEntity;
import com.weike.system.service.RolePermissionDevService;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.R;



/**
 * 
 *
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-10 14:03:35
 */
@RestController
@RequestMapping("system/rolepermissiondev")
public class RolePermissionDevController {
    @Autowired
    private RolePermissionDevService rolePermissionDevService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("system:rolepermissiondev:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = rolePermissionDevService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{permId}")
    //@RequiresPermissions("system:rolepermissiondev:info")
    public R info(@PathVariable("permId") String permId){
		RolePermissionDevEntity rolePermissionDev = rolePermissionDevService.getById(permId);

        return R.ok().put("rolePermissionDev", rolePermissionDev);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("system:rolepermissiondev:save")
    public R save(@RequestBody RolePermissionDevEntity rolePermissionDev){
		rolePermissionDevService.save(rolePermissionDev);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("system:rolepermissiondev:update")
    public R update(@RequestBody RolePermissionDevEntity rolePermissionDev){
		rolePermissionDevService.updateById(rolePermissionDev);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("system:rolepermissiondev:delete")
    public R delete(@RequestBody String[] permIds){
		rolePermissionDevService.removeByIds(Arrays.asList(permIds));

        return R.ok();
    }

}

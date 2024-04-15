package com.weike.system.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weike.system.entity.RolePermissionEntity;
import com.weike.system.service.RolePermissionService;
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
@RequestMapping("system/rolepermission")
public class RolePermissionController {
    @Autowired
    private RolePermissionService rolePermissionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("system:rolepermission:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = rolePermissionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{permId}")
    //@RequiresPermissions("system:rolepermission:info")
    public R info(@PathVariable("permId") String permId){
		RolePermissionEntity rolePermission = rolePermissionService.getById(permId);

        return R.ok().put("rolePermission", rolePermission);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("system:rolepermission:save")
    public R save(@RequestBody RolePermissionEntity rolePermission){
		rolePermissionService.save(rolePermission);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("system:rolepermission:update")
    public R update(@RequestBody RolePermissionEntity rolePermission){
		rolePermissionService.updateById(rolePermission);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("system:rolepermission:delete")
    public R delete(@RequestBody String[] permIds){
		rolePermissionService.removeByIds(Arrays.asList(permIds));

        return R.ok();
    }

}

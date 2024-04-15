package com.weike.system.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weike.system.entity.PermissionEntity;
import com.weike.system.service.PermissionService;
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
@RequestMapping("system/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("system:permission:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = permissionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{permId}")
    //@RequiresPermissions("system:permission:info")
    public R info(@PathVariable("permId") String permId){
		PermissionEntity permission = permissionService.getById(permId);

        return R.ok().put("permission", permission);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("system:permission:save")
    public R save(@RequestBody PermissionEntity permission){
		permissionService.save(permission);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("system:permission:update")
    public R update(@RequestBody PermissionEntity permission){
		permissionService.updateById(permission);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("system:permission:delete")
    public R delete(@RequestBody String[] permIds){
		permissionService.removeByIds(Arrays.asList(permIds));

        return R.ok();
    }

}

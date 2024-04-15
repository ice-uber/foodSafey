package com.weike.system.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weike.system.entity.RoleMenuDevEntity;
import com.weike.system.service.RoleMenuDevService;
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
@RequestMapping("system/rolemenudev")
public class RoleMenuDevController {
    @Autowired
    private RoleMenuDevService roleMenuDevService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("system:rolemenudev:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = roleMenuDevService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{menuId}")
    //@RequiresPermissions("system:rolemenudev:info")
    public R info(@PathVariable("menuId") String menuId){
		RoleMenuDevEntity roleMenuDev = roleMenuDevService.getById(menuId);

        return R.ok().put("roleMenuDev", roleMenuDev);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("system:rolemenudev:save")
    public R save(@RequestBody RoleMenuDevEntity roleMenuDev){
		roleMenuDevService.save(roleMenuDev);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("system:rolemenudev:update")
    public R update(@RequestBody RoleMenuDevEntity roleMenuDev){
		roleMenuDevService.updateById(roleMenuDev);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("system:rolemenudev:delete")
    public R delete(@RequestBody String[] menuIds){
		roleMenuDevService.removeByIds(Arrays.asList(menuIds));

        return R.ok();
    }

}

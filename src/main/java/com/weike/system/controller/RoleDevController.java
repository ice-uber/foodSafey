package com.weike.system.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weike.system.entity.RoleDevEntity;
import com.weike.system.service.RoleDevService;
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
@RequestMapping("system/roledev")
public class RoleDevController {
    @Autowired
    private RoleDevService roleDevService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("system:roledev:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = roleDevService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{roleId}")
    //@RequiresPermissions("system:roledev:info")
    public R info(@PathVariable("roleId") String roleId){
		RoleDevEntity roleDev = roleDevService.getById(roleId);

        return R.ok().put("roleDev", roleDev);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("system:roledev:save")
    public R save(@RequestBody RoleDevEntity roleDev){
		roleDevService.save(roleDev);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("system:roledev:update")
    public R update(@RequestBody RoleDevEntity roleDev){
		roleDevService.updateById(roleDev);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("system:roledev:delete")
    public R delete(@RequestBody String[] roleIds){
		roleDevService.removeByIds(Arrays.asList(roleIds));

        return R.ok();
    }

}

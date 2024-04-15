package com.weike.system.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weike.system.entity.ModuleEntity;
import com.weike.system.service.ModuleService;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.R;



/**
 * 菜单分类
 *
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-10 14:03:35
 */
@RestController
@RequestMapping("system/module")
public class ModuleController {
    @Autowired
    private ModuleService moduleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("system:module:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = moduleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{moduleId}")
    //@RequiresPermissions("system:module:info")
    public R info(@PathVariable("moduleId") String moduleId){
		ModuleEntity module = moduleService.getById(moduleId);

        return R.ok().put("module", module);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("system:module:save")
    public R save(@RequestBody ModuleEntity module){
		moduleService.save(module);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("system:module:update")
    public R update(@RequestBody ModuleEntity module){
		moduleService.updateById(module);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("system:module:delete")
    public R delete(@RequestBody String[] moduleIds){
		moduleService.removeByIds(Arrays.asList(moduleIds));

        return R.ok();
    }

}

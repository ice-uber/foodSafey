package com.weike.system.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weike.system.entity.DepartmentEntity;
import com.weike.system.service.DepartmentService;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.R;



/**
 * 地图表
 *
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-10 14:03:35
 */
@RestController
@RequestMapping("system/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("system:department:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = departmentService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{deptId}")
    //@RequiresPermissions("system:department:info")
    public R info(@PathVariable("deptId") String deptId){
		DepartmentEntity department = departmentService.getById(deptId);

        return R.ok().put("department", department);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("system:department:save")
    public R save(@RequestBody DepartmentEntity department){
		departmentService.save(department);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("system:department:update")
    public R update(@RequestBody DepartmentEntity department){
		departmentService.updateById(department);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("system:department:delete")
    public R delete(@RequestBody String[] deptIds){
		departmentService.removeByIds(Arrays.asList(deptIds));

        return R.ok();
    }

}

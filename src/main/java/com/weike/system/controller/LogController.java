package com.weike.system.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weike.system.entity.LogEntity;
import com.weike.system.service.LogService;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.R;



/**
 * 系统日志
 *
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-10 14:03:35
 */
@RestController
@RequestMapping("system/log")
public class LogController {
    @Autowired
    private LogService logService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("system:log:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = logService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{logId}")
    //@RequiresPermissions("system:log:info")
    public R info(@PathVariable("logId") String logId){
		LogEntity log = logService.getById(logId);

        return R.ok().put("log", log);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("system:log:save")
    public R save(@RequestBody LogEntity log){
		logService.save(log);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("system:log:update")
    public R update(@RequestBody LogEntity log){
		logService.updateById(log);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("system:log:delete")
    public R delete(@RequestBody String[] logIds){
		logService.removeByIds(Arrays.asList(logIds));

        return R.ok();
    }

}

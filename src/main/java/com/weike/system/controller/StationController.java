package com.weike.system.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weike.system.entity.StationEntity;
import com.weike.system.service.StationService;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.R;



/**
 * 职位表
 *
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-10 14:03:35
 */
@RestController
@RequestMapping("system/station")
public class StationController {
    @Autowired
    private StationService stationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("system:station:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{stationId}")
    //@RequiresPermissions("system:station:info")
    public R info(@PathVariable("stationId") String stationId){
		StationEntity station = stationService.getById(stationId);

        return R.ok().put("station", station);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("system:station:save")
    public R save(@RequestBody StationEntity station){
		stationService.save(station);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("system:station:update")
    public R update(@RequestBody StationEntity station){
		stationService.updateById(station);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("system:station:delete")
    public R delete(@RequestBody String[] stationIds){
		stationService.removeByIds(Arrays.asList(stationIds));

        return R.ok();
    }

}

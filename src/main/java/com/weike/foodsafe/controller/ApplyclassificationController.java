package com.weike.foodsafe.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weike.foodsafe.entity.ApplyclassificationEntity;
import com.weike.foodsafe.service.ApplyclassificationService;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.R;



/**
 * 
 *
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
@RestController
@RequestMapping("foodsafe/applyclassification")
public class ApplyclassificationController {
    @Autowired
    private ApplyclassificationService applyclassificationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("foodsafe:applyclassification:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = applyclassificationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{applyid}")
    //@RequiresPermissions("foodsafe:applyclassification:info")
    public R info(@PathVariable("applyid") String applyid){
		ApplyclassificationEntity applyclassification = applyclassificationService.getById(applyid);

        return R.ok().put("applyclassification", applyclassification);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("foodsafe:applyclassification:save")
    public R save(@RequestBody ApplyclassificationEntity applyclassification){
		applyclassificationService.save(applyclassification);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("foodsafe:applyclassification:update")
    public R update(@RequestBody ApplyclassificationEntity applyclassification){
		applyclassificationService.updateById(applyclassification);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("foodsafe:applyclassification:delete")
    public R delete(@RequestBody String[] applyids){
		applyclassificationService.removeByIds(Arrays.asList(applyids));

        return R.ok();
    }

}

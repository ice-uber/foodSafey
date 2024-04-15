package com.weike.foodsafe.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weike.foodsafe.entity.ClassificationEntity;
import com.weike.foodsafe.service.ClassificationService;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.R;



/**
 * 分类表
 *
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
@RestController
@RequestMapping("foodsafe/classification")
public class ClassificationController {
    @Autowired
    private ClassificationService classificationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("foodsafe:classification:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = classificationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{classificationid}")
    //@RequiresPermissions("foodsafe:classification:info")
    public R info(@PathVariable("classificationid") String classificationid){
		ClassificationEntity classification = classificationService.getById(classificationid);

        return R.ok().put("classification", classification);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("foodsafe:classification:save")
    public R save(@RequestBody ClassificationEntity classification){
		classificationService.save(classification);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("foodsafe:classification:update")
    public R update(@RequestBody ClassificationEntity classification){
		classificationService.updateById(classification);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("foodsafe:classification:delete")
    public R delete(@RequestBody String[] classificationids){
		classificationService.removeByIds(Arrays.asList(classificationids));

        return R.ok();
    }

}

package com.weike.foodsafe.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weike.foodsafe.entity.ClassificationBakEntity;
import com.weike.foodsafe.service.ClassificationBakService;
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
@RequestMapping("foodsafe/classificationbak")
public class ClassificationBakController {
    @Autowired
    private ClassificationBakService classificationBakService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("foodsafe:classificationbak:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = classificationBakService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{classificationid}")
    //@RequiresPermissions("foodsafe:classificationbak:info")
    public R info(@PathVariable("classificationid") String classificationid){
		ClassificationBakEntity classificationBak = classificationBakService.getById(classificationid);

        return R.ok().put("classificationBak", classificationBak);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("foodsafe:classificationbak:save")
    public R save(@RequestBody ClassificationBakEntity classificationBak){
		classificationBakService.save(classificationBak);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("foodsafe:classificationbak:update")
    public R update(@RequestBody ClassificationBakEntity classificationBak){
		classificationBakService.updateById(classificationBak);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("foodsafe:classificationbak:delete")
    public R delete(@RequestBody String[] classificationids){
		classificationBakService.removeByIds(Arrays.asList(classificationids));

        return R.ok();
    }

}

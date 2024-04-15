package com.weike.foodsafe.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weike.foodsafe.entity.MadegoodsEntity;
import com.weike.foodsafe.service.MadegoodsService;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.R;



/**
 * 食品来源表
 *
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
@RestController
@RequestMapping("foodsafe/madegoods")
public class MadegoodsController {
    @Autowired
    private MadegoodsService madegoodsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("foodsafe:madegoods:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = madegoodsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{madegoodsid}")
    //@RequiresPermissions("foodsafe:madegoods:info")
    public R info(@PathVariable("madegoodsid") String madegoodsid){
		MadegoodsEntity madegoods = madegoodsService.getById(madegoodsid);

        return R.ok().put("madegoods", madegoods);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("foodsafe:madegoods:save")
    public R save(@RequestBody MadegoodsEntity madegoods){
		madegoodsService.save(madegoods);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("foodsafe:madegoods:update")
    public R update(@RequestBody MadegoodsEntity madegoods){
		madegoodsService.updateById(madegoods);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("foodsafe:madegoods:delete")
    public R delete(@RequestBody String[] madegoodsids){
		madegoodsService.removeByIds(Arrays.asList(madegoodsids));

        return R.ok();
    }

}

package com.weike.foodsafe.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weike.foodsafe.entity.CheckEntity;
import com.weike.foodsafe.service.CheckService;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.R;



/**
 * 检测表
 *
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
@RestController
@RequestMapping("foodsafe/check")
public class CheckController {
    @Autowired
    private CheckService checkService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("foodsafe:check:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = checkService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{checkid}")
    //@RequiresPermissions("foodsafe:check:info")
    public R info(@PathVariable("checkid") String checkid){
		CheckEntity check = checkService.getById(checkid);

        return R.ok().put("check", check);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("foodsafe:check:save")
    public R save(@RequestBody CheckEntity check){
		checkService.save(check);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("foodsafe:check:update")
    public R update(@RequestBody CheckEntity check){
		checkService.updateById(check);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("foodsafe:check:delete")
    public R delete(@RequestBody String[] checkids){
		checkService.removeByIds(Arrays.asList(checkids));

        return R.ok();
    }

}

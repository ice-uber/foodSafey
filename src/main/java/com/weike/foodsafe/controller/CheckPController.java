package com.weike.foodsafe.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weike.foodsafe.entity.CheckPEntity;
import com.weike.foodsafe.service.CheckPService;
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
@RequestMapping("foodsafe/checkp")
public class CheckPController {
    @Autowired
    private CheckPService checkPService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("foodsafe:checkp:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = checkPService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("foodsafe:checkp:info")
    public R info(@PathVariable("id") String id){
		CheckPEntity checkP = checkPService.getById(id);

        return R.ok().put("checkP", checkP);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("foodsafe:checkp:save")
    public R save(@RequestBody CheckPEntity checkP){
		checkPService.save(checkP);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("foodsafe:checkp:update")
    public R update(@RequestBody CheckPEntity checkP){
		checkPService.updateById(checkP);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("foodsafe:checkp:delete")
    public R delete(@RequestBody String[] ids){
		checkPService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

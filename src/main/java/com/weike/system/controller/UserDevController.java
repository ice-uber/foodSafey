package com.weike.system.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weike.system.entity.UserDevEntity;
import com.weike.system.service.UserDevService;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.R;



/**
 * 
 *
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-10 14:03:35
 */
@RestController
@RequestMapping("system/userdev")
public class UserDevController {
    @Autowired
    private UserDevService userDevService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("system:userdev:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userDevService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{userId}")
    //@RequiresPermissions("system:userdev:info")
    public R info(@PathVariable("userId") String userId){
		UserDevEntity userDev = userDevService.getById(userId);

        return R.ok().put("userDev", userDev);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("system:userdev:save")
    public R save(@RequestBody UserDevEntity userDev){
		userDevService.save(userDev);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("system:userdev:update")
    public R update(@RequestBody UserDevEntity userDev){
		userDevService.updateById(userDev);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("system:userdev:delete")
    public R delete(@RequestBody String[] userIds){
		userDevService.removeByIds(Arrays.asList(userIds));

        return R.ok();
    }

}

package com.weike.system.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weike.system.entity.UserRoleDevEntity;
import com.weike.system.service.UserRoleDevService;
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
@RequestMapping("system/userroledev")
public class UserRoleDevController {
    @Autowired
    private UserRoleDevService userRoleDevService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("system:userroledev:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userRoleDevService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{userId}")
    //@RequiresPermissions("system:userroledev:info")
    public R info(@PathVariable("userId") String userId){
		UserRoleDevEntity userRoleDev = userRoleDevService.getById(userId);

        return R.ok().put("userRoleDev", userRoleDev);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("system:userroledev:save")
    public R save(@RequestBody UserRoleDevEntity userRoleDev){
		userRoleDevService.save(userRoleDev);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("system:userroledev:update")
    public R update(@RequestBody UserRoleDevEntity userRoleDev){
		userRoleDevService.updateById(userRoleDev);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("system:userroledev:delete")
    public R delete(@RequestBody String[] userIds){
		userRoleDevService.removeByIds(Arrays.asList(userIds));

        return R.ok();
    }

}

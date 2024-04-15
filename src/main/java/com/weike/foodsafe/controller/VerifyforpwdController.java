package com.weike.foodsafe.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weike.foodsafe.entity.VerifyforpwdEntity;
import com.weike.foodsafe.service.VerifyforpwdService;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.R;



/**
 * 密码验证
 *
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
@RestController
@RequestMapping("foodsafe/verifyforpwd")
public class VerifyforpwdController {
    @Autowired
    private VerifyforpwdService verifyforpwdService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("foodsafe:verifyforpwd:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = verifyforpwdService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{verifyid}")
    //@RequiresPermissions("foodsafe:verifyforpwd:info")
    public R info(@PathVariable("verifyid") String verifyid){
		VerifyforpwdEntity verifyforpwd = verifyforpwdService.getById(verifyid);

        return R.ok().put("verifyforpwd", verifyforpwd);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("foodsafe:verifyforpwd:save")
    public R save(@RequestBody VerifyforpwdEntity verifyforpwd){
		verifyforpwdService.save(verifyforpwd);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("foodsafe:verifyforpwd:update")
    public R update(@RequestBody VerifyforpwdEntity verifyforpwd){
		verifyforpwdService.updateById(verifyforpwd);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("foodsafe:verifyforpwd:delete")
    public R delete(@RequestBody String[] verifyids){
		verifyforpwdService.removeByIds(Arrays.asList(verifyids));

        return R.ok();
    }

}

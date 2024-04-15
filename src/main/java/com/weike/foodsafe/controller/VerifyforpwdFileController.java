package com.weike.foodsafe.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weike.foodsafe.entity.VerifyforpwdFileEntity;
import com.weike.foodsafe.service.VerifyforpwdFileService;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.R;



/**
 * 密码验证文件
 *
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
@RestController
@RequestMapping("foodsafe/verifyforpwdfile")
public class VerifyforpwdFileController {
    @Autowired
    private VerifyforpwdFileService verifyforpwdFileService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("foodsafe:verifyforpwdfile:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = verifyforpwdFileService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{fileid}")
    //@RequiresPermissions("foodsafe:verifyforpwdfile:info")
    public R info(@PathVariable("fileid") String fileid){
		VerifyforpwdFileEntity verifyforpwdFile = verifyforpwdFileService.getById(fileid);

        return R.ok().put("verifyforpwdFile", verifyforpwdFile);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("foodsafe:verifyforpwdfile:save")
    public R save(@RequestBody VerifyforpwdFileEntity verifyforpwdFile){
		verifyforpwdFileService.save(verifyforpwdFile);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("foodsafe:verifyforpwdfile:update")
    public R update(@RequestBody VerifyforpwdFileEntity verifyforpwdFile){
		verifyforpwdFileService.updateById(verifyforpwdFile);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("foodsafe:verifyforpwdfile:delete")
    public R delete(@RequestBody String[] fileids){
		verifyforpwdFileService.removeByIds(Arrays.asList(fileids));

        return R.ok();
    }

}

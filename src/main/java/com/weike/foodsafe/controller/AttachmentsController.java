package com.weike.foodsafe.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weike.foodsafe.entity.AttachmentsEntity;
import com.weike.foodsafe.service.AttachmentsService;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.R;



/**
 * 附件表
 *
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
@RestController
@RequestMapping("foodsafe/attachments")
public class AttachmentsController {
    @Autowired
    private AttachmentsService attachmentsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("foodsafe:attachments:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attachmentsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attachmentid}")
    //@RequiresPermissions("foodsafe:attachments:info")
    public R info(@PathVariable("attachmentid") String attachmentid){
		AttachmentsEntity attachments = attachmentsService.getById(attachmentid);

        return R.ok().put("attachments", attachments);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("foodsafe:attachments:save")
    public R save(@RequestBody AttachmentsEntity attachments){
		attachmentsService.save(attachments);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("foodsafe:attachments:update")
    public R update(@RequestBody AttachmentsEntity attachments){
		attachmentsService.updateById(attachments);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("foodsafe:attachments:delete")
    public R delete(@RequestBody String[] attachmentids){
		attachmentsService.removeByIds(Arrays.asList(attachmentids));

        return R.ok();
    }

}

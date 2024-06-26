package com.weike.foodsafe.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.weike.foodsafe.entity.DistributionEntity;
import com.weike.foodsafe.entity.PurchaserEntity;
import com.weike.foodsafe.vo.CooperationResVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.weike.foodsafe.entity.CooperationEntity;
import com.weike.foodsafe.service.CooperationService;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.R;



/**
 * 合作表
 *
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
@RestController
@RequestMapping("foodsafe/cooperation")
public class CooperationController {
    @Autowired
    private CooperationService cooperationService;
    /**
     * 取消申请合作
     * @param
     * @return
     */
    @GetMapping("unApply/{id}")
    public R unApplyCooperation(@PathVariable("id") String id){
        cooperationService.removeById(id);
        return R.ok();
    }

    /**
     * 申请合作
     * @param token
     * @return
     */
    @GetMapping("apply/{id}")
    public R applyCooperation(@RequestHeader String token,
                              @PathVariable("id") String id){
        cooperationService.applyCooperation(token , id);
        return R.ok();
    }


    /**
     * 获取还未合作的采购商列表
     * @param token
     * @return
     */
    @GetMapping("/list/unCooperation")
    public R unCooperationPurchaserList(@RequestHeader String token){
        List<PurchaserEntity> purchaserEntityList = cooperationService.unCooperationPurchaserList(token);
        return R.ok().put("data", purchaserEntityList);
    }

    /**
     * 获取所有的采购商列表
     * @param token
     * @return
     */
    @RequestMapping("/list/puschaser")
    public R purchaserList(@RequestHeader String token,
                           @RequestParam Map<String , Object> params){
        PageUtils page = cooperationService.getpuschaserList(token , params);
        return R.ok().put("data", page);
    }


    /**
     * 获取所有的配送商列表
     * @param token
     * @return
     */
    @RequestMapping("/list/distribution")
    public R distributionList(@RequestHeader String token){
        List<DistributionEntity> distributionEntities = cooperationService.getDistributionList(token);
        return R.ok().put("data", distributionEntities);
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("foodsafe:cooperation:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cooperationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{cooperationid}")
    //@RequiresPermissions("foodsafe:cooperation:info")
    public R info(@PathVariable("cooperationid") String cooperationid){
		CooperationEntity cooperation = cooperationService.getById(cooperationid);

        return R.ok().put("cooperation", cooperation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("foodsafe:cooperation:save")
    public R save(@RequestBody CooperationEntity cooperation){
		cooperationService.save(cooperation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("foodsafe:cooperation:update")
    public R update(@RequestBody CooperationEntity cooperation){
		cooperationService.updateById(cooperation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("foodsafe:cooperation:delete")
    public R delete(@RequestBody String[] cooperationids){
		cooperationService.removeByIds(Arrays.asList(cooperationids));

        return R.ok();
    }

}

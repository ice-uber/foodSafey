package com.weike.foodsafe.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.weike.foodsafe.service.PurchaserService;
import com.weike.foodsafe.vo.DistributionVo;
import com.weike.foodsafe.vo.PurchaserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.weike.foodsafe.entity.DistributionEntity;
import com.weike.foodsafe.service.DistributionService;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.R;



/**
 * 分配表
 *
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
@RestController
@RequestMapping("foodsafe/distribution")
public class DistributionController {
    @Autowired
    private DistributionService distributionService;

    /**
     * 列表
     */
    @RequestMapping("/cooperation/purchaser")
    public R purchaserList(@RequestHeader String token){
        List<PurchaserVo> result = distributionService.purchaserList(token);
        return R.ok().put("data", result);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("foodsafe:distribution:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = distributionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{distributionid}")
    //@RequiresPermissions("foodsafe:distribution:info")
    public R info(@PathVariable("distributionid") String distributionid){
		DistributionEntity distribution = distributionService.getById(distributionid);
        return R.ok().put("distribution", distribution);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("foodsafe:distribution:save")
    public R save(@RequestBody DistributionVo distributionVo){
		distributionService.saveDistrbution(distributionVo);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("foodsafe:distribution:update")
    public R update(@RequestBody DistributionEntity distribution){
		distributionService.updateById(distribution);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("foodsafe:distribution:delete")
    public R delete(@RequestBody String[] distributionids){
		distributionService.removeByIds(Arrays.asList(distributionids));

        return R.ok();
    }

}

package com.weike.foodsafe.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.weike.foodsafe.vo.goods.GoodsCountVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.weike.foodsafe.entity.GoodsEntity;
import com.weike.foodsafe.service.GoodsService;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.R;



/**
 * 商品表
 *
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
@RestController
@Slf4j
@RequestMapping("foodsafe/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    /**
     * 获取配送上的分页数据
     * @param distributionId
     * @param params
     * @return
     */
    @GetMapping("list/{distributionId}")
    public R listByDistributionId(@PathVariable("distributionId") String distributionId,
                                  @RequestParam Map<String , Object> params,
                                  @RequestHeader String token){
        PageUtils page = goodsService.listByDistributionId(distributionId , params , token);
        return R.ok().put("data", page);
    }

    /**
     * 统计各类状态的订单
     * @param distributionId
     * @return
     */
    @GetMapping("count/{distributionId}")
    public R goodsCountByDistributionId(@PathVariable("distributionId") String distributionId ){
        List<GoodsCountVo> goodsCountVo = goodsService.goodsCountByDistributionId(distributionId);
        return R.ok().put("data", goodsCountVo);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,
                  @RequestHeader String token){
        PageUtils page = goodsService.queryGoodsPage(params , token);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{goodsid}")
    //@RequiresPermissions("foodsafe:goods:info")
    public R info(@PathVariable("goodsid") String goodsid){
		GoodsEntity goods = goodsService.getById(goodsid);
        return R.ok().put("goods", goods);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("foodsafe:goods:save")
    public R save(@RequestBody GoodsEntity goods,
                  @RequestHeader String token){
		goodsService.saveGoods(goods , token);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("foodsafe:goods:update")
    public R update(@RequestBody GoodsEntity goods){
		goodsService.updateById(goods);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("foodsafe:goods:delete")
    public R delete(@RequestBody String[] goodsids){
		goodsService.removeByIds(Arrays.asList(goodsids));
        return R.ok();
    }

    /**
     * 上架
     */
    @GetMapping("/up/{goodsid}")
    public R up(@PathVariable("goodsid") String goodsid){
        goodsService.up(goodsid);
        return R.ok();
    }

    /**
     * 下架
     */
    @GetMapping("/down/{goodsid}")
    public R down(@PathVariable String goodsid){
        goodsService.down(goodsid);
        return R.ok();
    }

    /**
     * 批量下架
     * @param ids
     * @return
     */
    @PostMapping("/down/list")
    public R downList(@RequestBody List<String> ids){
        goodsService.listDown(ids);
        return R.ok();
    }

    /**
     * 批量上架
     * @param ids
     * @return
     */
    @PostMapping("/up/list")
    public R upList(@RequestBody List<String> ids){
        goodsService.listUp(ids);
        return R.ok();
    }

}

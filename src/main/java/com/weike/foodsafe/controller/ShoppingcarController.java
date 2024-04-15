package com.weike.foodsafe.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.weike.foodsafe.entity.GoodsEntity;
import com.weike.foodsafe.vo.shoppingCart.ShoppingCartResVo;
import com.weike.foodsafe.vo.shoppingCart.ShoppingCartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.weike.foodsafe.entity.ShoppingcarEntity;
import com.weike.foodsafe.service.ShoppingcarService;
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
@RequestMapping("foodsafe/shoppingcar")
public class ShoppingcarController {
    @Autowired
    private ShoppingcarService shoppingcarService;

    @DeleteMapping("list/remove/{distributionId}")
    public R removeList(@PathVariable("distributionId") String distributionId,
            @RequestHeader String token) {
        shoppingcarService.removeALlByToken(distributionId , token);
        return R.ok();
    }

    /**
     * 获取当前配送商下的购物车商品
     */
    @RequestMapping("list/{distributionId}")
    //@RequiresPermissions("foodsafe:shoppingcar:list")
    public R distributionList(@RequestHeader String token,
                              @PathVariable("distributionId") String distributionId){
        List<ShoppingCartResVo> shoppingCartResVos = shoppingcarService.distributionList(distributionId , token);
        return R.ok().put("data", shoppingCartResVos);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{carid}")
    //@RequiresPermissions("foodsafe:shoppingcar:info")
    public R info(@PathVariable("carid") String carid){
		ShoppingcarEntity shoppingcar = shoppingcarService.getById(carid);

        return R.ok().put("shoppingcar", shoppingcar);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ShoppingCartVo shoppingCartVo,
                  @RequestHeader String token){
		shoppingcarService.saveShoppingCart(shoppingCartVo ,token );
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("foodsafe:shoppingcar:update")
    public R update(@RequestBody ShoppingcarEntity shoppingcar){
		shoppingcarService.updateById(shoppingcar);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("foodsafe:shoppingcar:delete")
    public R delete(@RequestBody String[] carids){
		shoppingcarService.removeByIds(Arrays.asList(carids));
        return R.ok();
    }

}

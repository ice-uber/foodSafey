package com.weike.foodsafe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weike.common.utils.PageUtils;
import com.weike.foodsafe.entity.GoodsEntity;
import com.weike.foodsafe.entity.ShoppingcarEntity;
import com.weike.foodsafe.vo.shoppingCart.ShoppingCartResVo;
import com.weike.foodsafe.vo.shoppingCart.ShoppingCartVo;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
public interface ShoppingcarService extends IService<ShoppingcarEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveShoppingCart(ShoppingCartVo shoppingCartVo,  String token);

    List<ShoppingCartResVo> distributionList(String distributionId, String token);

    void removeALlByToken(String distributionId ,String token);
}


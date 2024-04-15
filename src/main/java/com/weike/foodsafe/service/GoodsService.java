package com.weike.foodsafe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weike.common.utils.PageUtils;
import com.weike.foodsafe.entity.GoodsEntity;
import com.weike.foodsafe.vo.goods.GoodsCountVo;

import java.util.List;
import java.util.Map;

/**
 * 商品表
 *
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
public interface GoodsService extends IService<GoodsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryGoodsPage(Map<String, Object> params ,String token);

    void up(String goodsid);

    void down(String goodsid);

    void listDown(List<String> ids);

    void listUp(List<String> ids);

    void saveGoods(GoodsEntity goods , String token);

    List<GoodsCountVo> goodsCountByDistributionId(String distributionId);

    PageUtils listByDistributionId(String distributionId, Map<String, Object> params , String token);
}


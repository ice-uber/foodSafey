package com.weike.foodsafe.dao;

import com.weike.foodsafe.entity.GoodsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品表
 * 
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
@Mapper
public interface GoodsDao extends BaseMapper<GoodsEntity> {
	
}

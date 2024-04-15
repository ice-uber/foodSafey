package com.weike.foodsafe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weike.common.utils.PageUtils;
import com.weike.foodsafe.entity.PurchaserEntity;

import java.util.Map;

/**
 * 采购商表
 *
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
public interface PurchaserService extends IService<PurchaserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    String getPurchaserIdByToken(String token);
}


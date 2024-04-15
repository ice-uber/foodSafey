package com.weike.foodsafe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weike.common.utils.PageUtils;
import com.weike.foodsafe.entity.SupplierEntity;

import java.util.List;
import java.util.Map;

/**
 * 供货商表
 *
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
public interface SupplierService extends IService<SupplierEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<SupplierEntity> getSupplierByToken(String token);

    PageUtils getSupplierPageByToken(Map<String , Object> params , String token);

    void saveSupplier(SupplierEntity supplier, String token);
}


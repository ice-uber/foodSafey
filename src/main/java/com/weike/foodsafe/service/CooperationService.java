package com.weike.foodsafe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weike.common.utils.PageUtils;
import com.weike.foodsafe.entity.CooperationEntity;
import com.weike.foodsafe.entity.DistributionEntity;
import com.weike.foodsafe.entity.PurchaserEntity;
import com.weike.foodsafe.vo.CooperationResVo;

import java.util.List;
import java.util.Map;

/**
 * 合作表
 *
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
public interface CooperationService extends IService<CooperationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<DistributionEntity> getDistributionList(String token);

    PageUtils getpuschaserList(String token , Map<String , Object> params);

    List<PurchaserEntity> unCooperationPurchaserList(String token);

    void applyCooperation(String token, String id);


}


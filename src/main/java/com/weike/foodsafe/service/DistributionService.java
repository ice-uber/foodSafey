package com.weike.foodsafe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weike.common.utils.PageUtils;
import com.weike.foodsafe.entity.DistributionEntity;
import com.weike.foodsafe.vo.DistributionVo;
import com.weike.foodsafe.vo.PurchaserVo;

import java.util.List;
import java.util.Map;

/**
 * 分配表
 *
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
public interface DistributionService extends IService<DistributionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    String getDistributionIdByToken(String token);

    List<PurchaserVo> purchaserList(String token);

    void saveDistrbution(DistributionVo distributionVo);

}


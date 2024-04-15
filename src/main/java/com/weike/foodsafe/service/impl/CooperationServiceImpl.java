package com.weike.foodsafe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.weike.foodsafe.dao.DistributionDao;
import com.weike.foodsafe.entity.DistributionEntity;
import com.weike.foodsafe.service.DistributionService;
import com.weike.foodsafe.service.PurchaserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.Query;

import com.weike.foodsafe.dao.CooperationDao;
import com.weike.foodsafe.entity.CooperationEntity;
import com.weike.foodsafe.service.CooperationService;


@Service("cooperationService")
public class CooperationServiceImpl extends ServiceImpl<CooperationDao, CooperationEntity> implements CooperationService {

    @Autowired
    private PurchaserService purchaserService;

    @Autowired
    private DistributionDao distributionDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CooperationEntity> page = this.page(
                new Query<CooperationEntity>().getPage(params),
                new QueryWrapper<CooperationEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 获取所有合作的列表
     * @param token
     * @return
     */
    @Override
    public List<DistributionEntity> getDistributionList(String token) {
        String purchaserId = purchaserService.getPurchaserIdByToken(token);

        List<CooperationEntity> cooperationEntities = this.list(new LambdaQueryWrapper<CooperationEntity>()
                .eq(CooperationEntity::getPurchaserid, purchaserId));

        List<String> distributionIds = cooperationEntities.stream().map(CooperationEntity::getDistributionid).collect(Collectors.toList());


        List<DistributionEntity> distributionEntities = null;
        if (distributionIds.size() > 0) {
            distributionEntities = distributionDao.selectBatchIds(distributionIds);
        }

        return distributionEntities;
    }

}
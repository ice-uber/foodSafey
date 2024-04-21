package com.weike.foodsafe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.weike.common.utils.JwtHelper;
import com.weike.foodsafe.dao.DistributionDao;
import com.weike.foodsafe.entity.DistributionEntity;
import com.weike.foodsafe.entity.PurchaserEntity;
import com.weike.foodsafe.service.DistributionService;
import com.weike.foodsafe.service.PurchaserService;
import com.weike.foodsafe.vo.CooperationResVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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

    @Autowired
    private JwtHelper jwtHelper;

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


    /**
     * 获取所有的配送商
     * @param token
     * @return
     */
    @Override
    public PageUtils getpuschaserList(String token , Map<String , Object> params) {
        String key = (String) params.get("key");
        String status = (String) params.get("status");

        LambdaQueryWrapper<PurchaserEntity> wrapper = new LambdaQueryWrapper<>();

        if (!StringUtils.isBlank(key)) {
            wrapper.like(PurchaserEntity :: getCompanyname , key);
        }

        LambdaQueryWrapper<CooperationEntity> cooperationEntityLambdaQueryWrapper = new LambdaQueryWrapper<>();

        if (!StringUtils.isBlank(status)) {
            cooperationEntityLambdaQueryWrapper.eq(CooperationEntity :: getStatus , status);
        }
        String userId = jwtHelper.getUserId(token);
        DistributionEntity distributionEntity = distributionDao.selectOne(new LambdaQueryWrapper<DistributionEntity>()
                .eq(DistributionEntity::getUserId, userId));
        String distributionid = distributionEntity.getDistributionid();
        cooperationEntityLambdaQueryWrapper.eq(CooperationEntity :: getDistributionid , distributionid);
        IPage<CooperationEntity> page = this.page(
                new Query<CooperationEntity>().getPage(params),
                cooperationEntityLambdaQueryWrapper
        );
        PageUtils pageUtils = new PageUtils(page);

        List<CooperationEntity> records = page.getRecords();
        List<String> purchaserIds = records.stream().map(CooperationEntity::getPurchaserid)
                .collect(Collectors.toList());
        List<CooperationResVo> cooperationResVos = null;
        if (purchaserIds.size() > 0) {
            List<PurchaserEntity> purchaserEntityList = purchaserService.list(wrapper.in(PurchaserEntity :: getPurchaserid , purchaserIds));

            cooperationResVos = purchaserEntityList.stream().map(purchaserEntity -> {
                CooperationResVo cooperationResVo = new CooperationResVo();
                BeanUtils.copyProperties(purchaserEntity, cooperationResVo);
                List<CooperationEntity> cooperationEntities = records.stream().filter(cooperationEntity
                                -> cooperationEntity.getPurchaserid().equals(purchaserEntity.getPurchaserid()))
                        .collect(Collectors.toList());
                BeanUtils.copyProperties(cooperationEntities.get(0), cooperationResVo);
                return cooperationResVo;
            }).collect(Collectors.toList());
        }


        pageUtils.setList(cooperationResVos);

        return pageUtils;
    }

    /**
     * 获取还未合作的配送商列表
     * @param token
     * @return
     */
    @Override
    public List<PurchaserEntity> unCooperationPurchaserList(String token) {
        String userId = jwtHelper.getUserId(token);
        String distributionIdByUserId = distributionDao.getDistributionIdByUserId(userId);

        List<CooperationEntity> cooperationEntities = this.list(new LambdaQueryWrapper<CooperationEntity>()
                .eq(CooperationEntity :: getDistributionid , distributionIdByUserId));

        // 收集所有的采购商id
        List<String> purchaserIds = cooperationEntities.stream().map(CooperationEntity::getPurchaserid).collect(Collectors.toList());
        List<PurchaserEntity> purchaserEntityList;
        if (purchaserIds.size() > 0) {
            purchaserEntityList = purchaserService.list(new LambdaQueryWrapper<PurchaserEntity>()
                    .notIn(PurchaserEntity::getPurchaserid, purchaserIds));
        }else {
            purchaserEntityList = new ArrayList<>();
        }


        return purchaserEntityList;

    }

    /**
     * 申请合作
     * @param token
     * @param id
     */
    @Override
    public void applyCooperation(String token, String id) {
        String userId = jwtHelper.getUserId(token);
        String distributionIdByUserId = distributionDao.getDistributionIdByUserId(userId);

        CooperationEntity cooperationEntity = new CooperationEntity();
        cooperationEntity.setCooperationid(UUID.randomUUID().toString());


        if (distributionIdByUserId != null) {
            cooperationEntity.setDistributionid(distributionIdByUserId);
            cooperationEntity.setPurchaserid(id);
            cooperationEntity.setStatus("2");
        } else {
            String purchaserIdByToken = purchaserService.getPurchaserIdByToken(token);
            cooperationEntity.setDistributionid(id);
            cooperationEntity.setPurchaserid(purchaserIdByToken);
            cooperationEntity.setStatus("3");
        }

        this.save(cooperationEntity);

    }



}
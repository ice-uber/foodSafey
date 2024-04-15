package com.weike.foodsafe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.weike.common.utils.JwtHelper;
import com.weike.foodsafe.entity.AttachmentsEntity;
import com.weike.foodsafe.entity.CooperationEntity;
import com.weike.foodsafe.entity.PurchaserEntity;
import com.weike.foodsafe.service.AttachmentsService;
import com.weike.foodsafe.service.CooperationService;
import com.weike.foodsafe.service.PurchaserService;
import com.weike.foodsafe.vo.DistributionVo;
import com.weike.foodsafe.vo.PurchaserVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.Query;

import com.weike.foodsafe.dao.DistributionDao;
import com.weike.foodsafe.entity.DistributionEntity;
import com.weike.foodsafe.service.DistributionService;


@Service("distributionService")
public class DistributionServiceImpl extends ServiceImpl<DistributionDao, DistributionEntity> implements DistributionService {

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private CooperationService cooperationService;

    @Autowired
    private PurchaserService purchaserService;

    @Autowired
    private AttachmentsService attachmentsService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DistributionEntity> page = this.page(
                new Query<DistributionEntity>().getPage(params),
                new QueryWrapper<DistributionEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 根据token获取配送商id
     * @param token
     * @return
     */
    @Override
    public String getDistributionIdByToken(String token) {
        String userId = jwtHelper.getUserId(token);

        DistributionEntity distributionEntity = this.getOne(new LambdaQueryWrapper<DistributionEntity>()
                .eq(DistributionEntity::getUserId, userId));

        String id = "";
        if (distributionEntity != null) {
            id = distributionEntity.getDistributionid();
        }

        return id;
    }

    /**
     * 获取合作的采购商列表
     * @param token
     * @return
     */
    @Override
    public List<PurchaserVo> purchaserList(String token) {
        String distributionId = this.getDistributionIdByToken(token);

        // 获取所有的合作关系
        List<CooperationEntity> cooperationEntities = cooperationService.list(new LambdaQueryWrapper<CooperationEntity>()
                .eq(CooperationEntity::getDistributionid, distributionId));

        // 获取所有的采购商id
        List<String> purchaserIds = cooperationEntities.stream().map(CooperationEntity::getPurchaserid).collect(Collectors.toList());

        // 查询所有的采购商信息
        List<PurchaserEntity> purchaserEntityList = purchaserService.listByIds(purchaserIds);

        List<PurchaserVo> purchaserVos = purchaserEntityList.stream().map(purchaserEntity -> {
            PurchaserVo purchaserVo = new PurchaserVo();
            purchaserVo.setPurchaserId(purchaserEntity.getPurchaserid());
            purchaserVo.setPurchaserName(purchaserEntity.getCompanyname());
            return purchaserVo;
        }).collect(Collectors.toList());

        return purchaserVos;
    }

    /**
     * 保存单位信息
     * @param distributionVo
     */
    @Override
    public void saveDistrbution(DistributionVo distributionVo) {
        DistributionEntity distributionEntity = new DistributionEntity();
        BeanUtils.copyProperties(distributionVo , distributionEntity);
        System.out.println(distributionVo);
        this.updateById(distributionEntity);

        if ( !StringUtils.isBlank(distributionVo.getBusinessCardImg()) || !StringUtils.isBlank(distributionVo.getBusinessCardImg()) ){
            AttachmentsEntity attachmentsEntity = new AttachmentsEntity();
            attachmentsEntity.setRelationid(distributionVo.getDistributionid());
            if (!StringUtils.isBlank(distributionVo.getBusinessCardImg())) {{
                AttachmentsEntity attachments = attachmentsService.getOne(new LambdaQueryWrapper<AttachmentsEntity>()
                        .eq(AttachmentsEntity::getType, "1")
                        .eq(AttachmentsEntity::getRelationid, distributionVo.getDistributionid()));
                attachmentsEntity.setUrl(distributionVo.getBusinessCardImg());
                attachmentsEntity.setType("1");

                attachmentsEntity.setRealname("营业证件");
                if (attachments != null ) {
                    attachmentsEntity.setAttachmentid(attachments.getAttachmentid());
                    attachmentsService.updateById(attachmentsEntity);
                } else {
                    attachmentsEntity.setAttachmentid(UUID.randomUUID().toString());
                    attachmentsService.save(attachmentsEntity);
                }

            }}

            if (!StringUtils.isBlank(distributionVo.getIdentificationCardImg())) {{
                attachmentsEntity.setAttachmentid(UUID.randomUUID().toString());
                attachmentsEntity.setUrl(distributionVo.getIdentificationCardImg());
                attachmentsEntity.setRealname("法人身份证");
                attachmentsEntity.setType("2");

                AttachmentsEntity entity = attachmentsService.getOne(new LambdaQueryWrapper<AttachmentsEntity>()
                        .eq(AttachmentsEntity::getType, "2")
                        .eq(AttachmentsEntity::getRelationid, distributionVo.getDistributionid()));

                if (entity != null) {
                    attachmentsEntity.setAttachmentid(entity.getAttachmentid());
                    attachmentsService.updateById(attachmentsEntity);
                } else {
                    attachmentsEntity.setAttachmentid(UUID.randomUUID().toString());
                    attachmentsService.save(attachmentsEntity);
                }
            }}
        }



    }

}
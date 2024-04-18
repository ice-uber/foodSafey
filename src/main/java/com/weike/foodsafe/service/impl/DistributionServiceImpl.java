package com.weike.foodsafe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.weike.common.constant.OrderConstant;
import com.weike.common.utils.JwtHelper;
import com.weike.foodsafe.entity.*;
import com.weike.foodsafe.service.*;
import com.weike.foodsafe.vo.DistributionVo;
import com.weike.foodsafe.vo.PurchaserVo;
import com.weike.foodsafe.vo.distrobution.DistributionResVo;
import com.weike.foodsafe.vo.goods.GoodsCountVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

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


    /**
     * 获取配送商下的配送商列表
     * @param params
     * @param token
     * @return
     */
    @Override
    public PageUtils distributionList(Map<String, Object> params, String token) {
        String key = (String) params.get("key");
        String status = (String) params.get("status");
        String purchaserIdByToken = purchaserService.getPurchaserIdByToken(token);

        LambdaQueryWrapper<DistributionEntity> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isBlank(key)) {
            wrapper.like(DistributionEntity :: getCompanyname , key);
        }

        LambdaQueryWrapper<CooperationEntity> cooperationEntityLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isBlank(status)) {
            cooperationEntityLambdaQueryWrapper.like(CooperationEntity :: getStatus , status);
        }

        IPage<CooperationEntity> page = cooperationService.page(
                new Query<CooperationEntity>().getPage(params),
                cooperationEntityLambdaQueryWrapper
                        .eq(CooperationEntity::getPurchaserid, purchaserIdByToken)
        );

        PageUtils pageUtils = new PageUtils(page);

        List<CooperationEntity> records = page.getRecords();


        List<DistributionResVo> distributionResVos = null;
        if (records.size() > 0) {
            // 收集所有配送商的id
            List<String> distributionIds = records.stream().map(CooperationEntity::getDistributionid).collect(Collectors.toList());

            // 获取所有的配送商
            List<DistributionEntity> distributionEntities = this.list(wrapper.in(DistributionEntity::getDistributionid, distributionIds));

            // 获取筛选完的配送商id
            List<String> filterDistributionIds = distributionEntities.stream().map(DistributionEntity::getDistributionid).collect(Collectors.toList());

            // 获取全部配送商附件
            List<AttachmentsEntity> attachmentsEntityList = attachmentsService.list(new LambdaQueryWrapper<AttachmentsEntity>()
                    .in(AttachmentsEntity::getRelationid, filterDistributionIds));

            // 构造返回结果
            distributionResVos = distributionEntities.stream().map(distributionEntity -> {
                DistributionResVo distributionResVo = new DistributionResVo();
                BeanUtils.copyProperties(distributionEntity, distributionResVo);

                // 设置商家图片列表
                List<AttachmentsEntity> attachmentsEntities = attachmentsEntityList.stream().filter(attachmentsEntity
                                -> attachmentsEntity.getRelationid().equals(distributionEntity.getDistributionid()))
                        .collect(Collectors.toList());

                List<String> imgList = attachmentsEntities.stream().map(AttachmentsEntity::getUrl).collect(Collectors.toList());
                distributionResVo.setImgList(imgList);

                // 设置商品种类
                List<GoodsCountVo> goodsCountVos = goodsService.goodsCountByDistributionId(distributionEntity.getDistributionid());
                List<GoodsCountVo> collect = goodsCountVos.stream().sorted((item1, item2) -> item2.getCount() - item1.getCount()).collect(Collectors.toList());
                distributionResVo.setGoodsCount(collect);

                // 设置商品合作明细
                List<CooperationEntity> cooperationEntities = records.stream().filter(cooperationEntity
                                -> cooperationEntity.getDistributionid().equals(distributionEntity.getDistributionid()))
                        .collect(Collectors.toList());
                if (cooperationEntities.size() > 0) {
                    distributionResVo.setCooperation(cooperationEntities.get(0));
                }

                // 设置店铺图片
                distributionResVo.setShopImg(distributionEntity.getShopImg());

                // 设置来往记录
                List<OrderEntity> orderEntityList = orderService.list(new LambdaQueryWrapper<OrderEntity>()
                        .eq(OrderEntity::getDistributionid, distributionEntity.getDistributionid())
                        .eq(OrderEntity::getPurchaserid, purchaserIdByToken)
                        .eq(OrderEntity::getStatus, "4"));

                distributionResVo.setSumFinishOrder(orderEntityList.size());
                // 计算总金额
                BigDecimal totalMoney = new BigDecimal("0");
                for (OrderEntity orderEntity : orderEntityList) {
                    BigDecimal money = orderEntity.getMoney();
                    totalMoney = totalMoney.add(money);
                }

                distributionResVo.setSumMoney(totalMoney);

                return distributionResVo;
            }).collect(Collectors.toList());
        }
        pageUtils.setList(distributionResVos);
        return pageUtils;
    }

}
package com.weike.foodsafe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.weike.common.utils.JwtHelper;
import com.weike.foodsafe.dao.PurchaserDao;
import com.weike.foodsafe.dao.ShoppingcarDao;
import com.weike.foodsafe.entity.*;
import com.weike.foodsafe.service.ClassificationService;
import com.weike.foodsafe.service.DistributionService;
import com.weike.foodsafe.service.ShoppingcarService;
import com.weike.foodsafe.vo.GoodsVo;
import com.weike.foodsafe.vo.goods.GoodsCountVo;
import com.weike.foodsafe.vo.shoppingCart.ShoppingCartResVo;
import com.weike.foodsafe.vo.shoppingCart.ShoppingCartVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.Query;

import com.weike.foodsafe.dao.GoodsDao;
import com.weike.foodsafe.service.GoodsService;
import org.springframework.transaction.annotation.Transactional;


@Service("goodsService")
public class GoodsServiceImpl extends ServiceImpl<GoodsDao, GoodsEntity> implements GoodsService {

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private ClassificationService classificationService;

    @Autowired
    private DistributionService distributionService;

    @Autowired
    private ShoppingcarDao shoppingcarDao;

    @Autowired
    private PurchaserDao purchaserDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<GoodsEntity> page = this.page(
                new Query<GoodsEntity>().getPage(params),
                new QueryWrapper<GoodsEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 根据条件查询商品列表
     *
     * @param params
     * @return
     */
    @Override
    public PageUtils queryGoodsPage(Map<String, Object> params, String token) {
        String key = (String) params.get("key");
        String categoryId = (String) params.get("categoryId");
        String status = (String) params.get("status");
        LambdaQueryWrapper<GoodsEntity> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isBlank(key)) {
            wrapper.like(GoodsEntity::getGoodsname, key);
        }
        if (!StringUtils.isBlank(categoryId)) {
            wrapper.eq(GoodsEntity::getClassificationid, categoryId);
        }
        if (!StringUtils.isBlank(status)) {
            wrapper.eq(GoodsEntity::getStatus, status);
        }

        String userId = jwtHelper.getUserId(token);

        // 查询当前账号属于哪个配送商
        DistributionEntity distributionEntity = distributionService.getOne(new LambdaQueryWrapper<DistributionEntity>()
                .eq(DistributionEntity::getUserId, userId));

        wrapper.eq(GoodsEntity::getDistributionid, distributionEntity.getDistributionid());
        IPage<GoodsEntity> page = this.page(
                new Query<GoodsEntity>().getPage(params),
                wrapper
        );
        List<GoodsEntity> records = page.getRecords();
        List<GoodsVo> collect = records.stream().map(goodsEntity -> {
            GoodsVo goodsVo = new GoodsVo();
            BeanUtils.copyProperties(goodsEntity, goodsVo);
            ClassificationEntity classificationEntity = classificationService.getOne(new LambdaQueryWrapper<ClassificationEntity>()
                    .eq(ClassificationEntity::getClassificationid, goodsEntity.getClassificationid()));
            if (classificationEntity != null) {

                goodsVo.setCategoryName(classificationEntity.getClassificationname());
            }
            return goodsVo;
        }).collect(Collectors.toList());
        PageUtils pageUtils = new PageUtils(page);
        pageUtils.setList(collect);
        return pageUtils;
    }

    /**
     * 上架
     *
     * @param goodsid
     */
    @Override
    public void up(String goodsid) {
        GoodsEntity goodsEntity = new GoodsEntity();
        goodsEntity.setGoodsid(goodsid);
        goodsEntity.setStatus("1");
        this.updateById(goodsEntity);
    }

    /**
     * 下架
     *
     * @param goodsid
     */
    @Override
    public void down(String goodsid) {
        GoodsEntity goodsEntity = new GoodsEntity();
        goodsEntity.setGoodsid(goodsid);
        goodsEntity.setStatus("0");
        this.updateById(goodsEntity);
    }

    /**
     * 批量下架
     *
     * @param ids
     */
    @Transactional
    @Override
    public void listDown(List<String> ids) {

        List<GoodsEntity> goodsEntityList = ids.stream().map(goodsid -> {
            GoodsEntity goodsEntity = new GoodsEntity();
            goodsEntity.setGoodsid(goodsid);
            goodsEntity.setStatus("0");
            return goodsEntity;
        }).collect(Collectors.toList());

        // 批量下架
        this.updateBatchById(goodsEntityList);

    }

    /**
     * 批量上架
     *
     * @param ids
     */
    @Override
    public void listUp(List<String> ids) {
        List<GoodsEntity> goodsEntityList = ids.stream().map(goodsid -> {
            GoodsEntity goodsEntity = new GoodsEntity();
            goodsEntity.setGoodsid(goodsid);
            goodsEntity.setStatus("1");
            return goodsEntity;
        }).collect(Collectors.toList());

        // 批量上架
        this.updateBatchById(goodsEntityList);


    }

    @Override
    public void saveGoods(GoodsEntity goods, String token) {
        String distributionIdByToken = distributionService.getDistributionIdByToken(token);
        goods.setDistributionid(distributionIdByToken);
        goods.setStatus("0");
        this.save(goods);
    }

    /**
     * 统计配送商各类产品数量
     *
     * @param distributionId
     * @return
     */
    @Override
    public List<GoodsCountVo> goodsCountByDistributionId(String distributionId) {

        // 查询出所有分类
        List<ClassificationEntity> classificationEntities = classificationService.list();

        // 查循出所有一级分类
        List<ClassificationEntity> classificationLevelOneEntities = classificationService.list(
                new LambdaQueryWrapper<ClassificationEntity>()
                        .eq(ClassificationEntity::getParentid, "-1")
        );

        // 获取所有商品
        List<GoodsEntity> goodsEntities = this.list(new LambdaQueryWrapper<GoodsEntity>()
                .eq(GoodsEntity::getDistributionid, distributionId));

        // 获取所有的分类id
        List<String> categoryIds = goodsEntities.stream().map(GoodsEntity::getClassificationid).filter(Objects::nonNull)
                .collect(Collectors.toList());

        // 构造结果
        List<GoodsCountVo> goodsCountVoList = classificationLevelOneEntities.stream().map(classificationEntity -> {
            GoodsCountVo goodsCountVo = new GoodsCountVo();
            goodsCountVo.setCount(0);
            goodsCountVo.setCategoryName(classificationEntity.getClassificationname());
            goodsCountVo.setCategoryId(classificationEntity.getClassificationid());
            goodsCountVo.setUrl(classificationEntity.getImgurl());
            return goodsCountVo;
        }).collect(Collectors.toList());

        // 统计各分类商品数量
        HashMap<String, String> hashMap = new HashMap<>();
        categoryIds.forEach(categoryId -> {

            String maxLevelCategoryId = getMaxLevelCategoryId(classificationEntities, categoryId);
            System.out.println("maxLevelCategoryId = " + maxLevelCategoryId);
            goodsCountVoList.forEach(goodsCountVo -> {
                if (goodsCountVo.getCategoryId().equals(maxLevelCategoryId)) {
                    goodsCountVo.setCount(goodsCountVo.getCount() + 1);
                }

            });


        });

        return goodsCountVoList;
    }

    /**
     * 获取配送商商品分页数据
     * @param distributionId
     * @param params
     * @return
     */
    @Override
    public PageUtils listByDistributionId(String distributionId, Map<String, Object> params , String token) {
        String key = (String) params.get("key");
        String userId = jwtHelper.getUserId(token);
        PurchaserEntity purchaserEntity = purchaserDao.selectOne(new LambdaQueryWrapper<PurchaserEntity>()
                .eq(PurchaserEntity::getUserId, userId));
        LambdaQueryWrapper<GoodsEntity> wrapper = new LambdaQueryWrapper<>();

        if (!StringUtils.isBlank(key)) {
            wrapper.like(GoodsEntity :: getGoodsname , key);
        }

        wrapper.eq(GoodsEntity :: getStatus , "1");
        wrapper.eq(GoodsEntity :: getDistributionid , distributionId);

        IPage<GoodsEntity> page = this.page(
                new Query<GoodsEntity>().getPage(params),
                wrapper
        );
        PageUtils pageUtils = new PageUtils(page);
        List<ShoppingCartResVo> shoppingCartResVos = page.getRecords().stream().map(goodsEntity -> {
            ShoppingCartResVo shoppingCartResVo = new ShoppingCartResVo();
            BeanUtils.copyProperties(goodsEntity, shoppingCartResVo);
            ShoppingcarEntity shoppingcarEntity = shoppingcarDao.selectOne(new LambdaQueryWrapper<ShoppingcarEntity>()
                    .eq(ShoppingcarEntity::getDistributionId, distributionId)
                    .eq(ShoppingcarEntity::getPurchaserid, purchaserEntity.getPurchaserid())
                    .eq(ShoppingcarEntity::getMadegoodsid, goodsEntity.getGoodsid()));
            if (shoppingcarEntity != null) {
                shoppingCartResVo.setNumber(shoppingcarEntity.getAmount().intValue());
            } else {
                shoppingCartResVo.setNumber(0);
            }
            return shoppingCartResVo;
        }).collect(Collectors.toList());
        pageUtils.setList(shoppingCartResVos);
        return pageUtils;
    }

    /**
     * 递归找出顶级父类id
     * @param classificationEntities
     * @param categoryId
     * @return
     */
    private String getMaxLevelCategoryId(List<ClassificationEntity> classificationEntities, String categoryId) {

        // 找出当前分类的信息
        List<ClassificationEntity> collect = classificationEntities.stream().filter(ClassificationEntity -> ClassificationEntity.getClassificationid().equals(categoryId))
                .collect(Collectors.toList());

        // 找出父分类
        List<ClassificationEntity> entities = classificationEntities.stream()
                .filter(classificationEntity -> classificationEntity.getClassificationid().equals(collect.get(0).getParentid()))
                .collect(Collectors.toList());

        if (!entities.get(0).getParentid().equals("-1")) {
            return getMaxLevelCategoryId(classificationEntities, entities.get(0).getClassificationid());
        }else {
            System.out.println(entities.get(0).getClassificationid());
            return entities.get(0).getClassificationid();
        }

    }

}
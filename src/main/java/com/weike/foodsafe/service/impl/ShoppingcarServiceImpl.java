package com.weike.foodsafe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.weike.common.utils.JwtHelper;
import com.weike.foodsafe.entity.DistributionEntity;
import com.weike.foodsafe.entity.GoodsEntity;
import com.weike.foodsafe.service.GoodsService;
import com.weike.foodsafe.service.PurchaserService;
import com.weike.foodsafe.vo.shoppingCart.ShoppingCartResVo;
import com.weike.foodsafe.vo.shoppingCart.ShoppingCartVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.Query;

import com.weike.foodsafe.dao.ShoppingcarDao;
import com.weike.foodsafe.entity.ShoppingcarEntity;
import com.weike.foodsafe.service.ShoppingcarService;


@Service("shoppingcarService")
public class ShoppingcarServiceImpl extends ServiceImpl<ShoppingcarDao, ShoppingcarEntity> implements ShoppingcarService {

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private PurchaserService purchaserService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ShoppingcarEntity> page = this.page(
                new Query<ShoppingcarEntity>().getPage(params),
                new QueryWrapper<ShoppingcarEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 新增或修改购物车商品数量
     * @param shoppingCartVo
     */
    @Override
    public void saveShoppingCart(ShoppingCartVo shoppingCartVo ,  String token) {
        // 获取当前用户ID
        String userId = jwtHelper.getUserId(token);
        if (!StringUtils.isBlank(userId)) {
            GoodsEntity goodsEntity = goodsService.getById(shoppingCartVo.getGoodsId());

            String purchaserId = purchaserService.getPurchaserIdByToken(token);

            // 查询当前用户的购物车下有无该商品 有就更新 无就新增
            ShoppingcarEntity shoppingcarEntity = this.getOne(new LambdaQueryWrapper<ShoppingcarEntity>()
                    .eq(ShoppingcarEntity::getDistributionId, shoppingCartVo.getDistributionId())
                    .eq(ShoppingcarEntity :: getPurchaserid , purchaserId)
                    .eq(ShoppingcarEntity :: getMadegoodsid , shoppingCartVo.getGoodsId()));
            if (shoppingcarEntity != null) {
                // 如果设置的数量为0则删除购物车数据
                if (shoppingCartVo.getAmount() <=  0) {
                    this.removeById(shoppingcarEntity.getCarid());
                }
                shoppingcarEntity.setAmount(new BigDecimal(shoppingCartVo.getAmount()));

                this.updateById(shoppingcarEntity);
            } else {
                ShoppingcarEntity shoppingCart = new ShoppingcarEntity();
                shoppingCart.setAmount(new BigDecimal(shoppingCartVo.getAmount()));
                shoppingCart.setCarid(UUID.randomUUID().toString());
                shoppingCart.setAdduser(userId);
                shoppingCart.setMadegoodsid(shoppingCartVo.getGoodsId());
                shoppingCart.setDistributionId(shoppingCartVo.getDistributionId());
                shoppingCart.setPurchaserid(purchaserId);
                this.save(shoppingCart);
            }

        }
    }

    /**
     * 获取当前配送商下的购物车商品数量
     * @param distributionId
     * @param token
     * @return
     */
    @Override
    public List<ShoppingCartResVo> distributionList(String distributionId, String token) {
        String purchaserId = purchaserService.getPurchaserIdByToken(token);

        List<ShoppingcarEntity> shoppingcarEntityList = this.list(new LambdaQueryWrapper<ShoppingcarEntity>()
                .eq(ShoppingcarEntity::getDistributionId, distributionId)
                .eq(ShoppingcarEntity::getPurchaserid, purchaserId));
        List<ShoppingCartResVo> shoppingCartResVos = null;
        if (shoppingcarEntityList.size() > 0) {
            List<String> goodsIds = shoppingcarEntityList.stream().map(ShoppingcarEntity::getMadegoodsid).collect(Collectors.toList());
            List<GoodsEntity> goodsEntities = goodsService.listByIds(goodsIds);
            shoppingCartResVos = goodsEntities.stream().map(goodsEntity -> {
                ShoppingCartResVo shoppingCartVo = new ShoppingCartResVo();
                BeanUtils.copyProperties(goodsEntity, shoppingCartVo);
                List<ShoppingcarEntity> shoppingcarEntities = shoppingcarEntityList.stream().filter(shoppingcarEntity
                                -> shoppingcarEntity.getMadegoodsid().equals(goodsEntity.getGoodsid()))
                        .collect(Collectors.toList());
                shoppingCartVo.setNumber(shoppingcarEntities.get(0).getAmount().intValue());
                return shoppingCartVo;
            }).collect(Collectors.toList());

        }
        return shoppingCartResVos;
    }

    /**
     * 清空当前配送商下的购物车
     * @param distributionId
     * @param token
     */
    @Override
    public void removeALlByToken(String distributionId, String token) {
        String purchaserId = purchaserService.getPurchaserIdByToken(token);

        this.remove(new LambdaQueryWrapper<ShoppingcarEntity>()
                .eq(ShoppingcarEntity :: getPurchaserid , purchaserId)
                .eq(ShoppingcarEntity :: getDistributionId , distributionId));

    }

}
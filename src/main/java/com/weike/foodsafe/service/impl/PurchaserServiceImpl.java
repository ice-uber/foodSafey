package com.weike.foodsafe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.weike.common.utils.JwtHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.Query;

import com.weike.foodsafe.dao.PurchaserDao;
import com.weike.foodsafe.entity.PurchaserEntity;
import com.weike.foodsafe.service.PurchaserService;


@Service("purchaserService")
public class PurchaserServiceImpl extends ServiceImpl<PurchaserDao, PurchaserEntity> implements PurchaserService {
    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaserEntity> page = this.page(
                new Query<PurchaserEntity>().getPage(params),
                new QueryWrapper<PurchaserEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 根据token获取采购商id
     *
     * @param token
     * @return
     */
    @Override
    public String getPurchaserIdByToken(String token) {
        String userId = jwtHelper.getUserId(token);

        PurchaserEntity purchaserEntity = this.getOne(new LambdaQueryWrapper<PurchaserEntity>()
                .eq(PurchaserEntity::getUserId, userId));


        return purchaserEntity.getPurchaserid();
    }

}
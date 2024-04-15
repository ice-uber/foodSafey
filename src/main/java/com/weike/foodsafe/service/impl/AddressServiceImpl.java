package com.weike.foodsafe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.weike.common.utils.JwtHelper;
import com.weike.foodsafe.dao.PurchaserDao;
import com.weike.foodsafe.entity.PurchaserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.Query;

import com.weike.foodsafe.dao.AddressDao;
import com.weike.foodsafe.entity.AddressEntity;
import com.weike.foodsafe.service.AddressService;


@Service("addressService")
public class AddressServiceImpl extends ServiceImpl<AddressDao, AddressEntity> implements AddressService {

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private PurchaserDao purchaserDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AddressEntity> page = this.page(
                new Query<AddressEntity>().getPage(params),
                new QueryWrapper<AddressEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 获取当前用户的全部地址
     * @param token
     * @return
     */
    @Override
    public List<AddressEntity> getBypurchaserId(String token) {
        String userId = jwtHelper.getUserId(token);
        PurchaserEntity purchaserEntity = purchaserDao.selectOne(new LambdaQueryWrapper<PurchaserEntity>()
                .eq(PurchaserEntity::getUserId, userId));
        List<AddressEntity> addressEntities = this.list(new LambdaQueryWrapper<AddressEntity>()
                .eq(AddressEntity::getPurchaserid, purchaserEntity.getPurchaserid()));
        return addressEntities;
    }

}
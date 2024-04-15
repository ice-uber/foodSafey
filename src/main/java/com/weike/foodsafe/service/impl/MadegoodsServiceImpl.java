package com.weike.foodsafe.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.Query;

import com.weike.foodsafe.dao.MadegoodsDao;
import com.weike.foodsafe.entity.MadegoodsEntity;
import com.weike.foodsafe.service.MadegoodsService;


@Service("madegoodsService")
public class MadegoodsServiceImpl extends ServiceImpl<MadegoodsDao, MadegoodsEntity> implements MadegoodsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MadegoodsEntity> page = this.page(
                new Query<MadegoodsEntity>().getPage(params),
                new QueryWrapper<MadegoodsEntity>()
        );

        return new PageUtils(page);
    }

}
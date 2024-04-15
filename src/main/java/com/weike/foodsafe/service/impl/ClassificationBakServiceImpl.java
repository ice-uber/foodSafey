package com.weike.foodsafe.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.Query;

import com.weike.foodsafe.dao.ClassificationBakDao;
import com.weike.foodsafe.entity.ClassificationBakEntity;
import com.weike.foodsafe.service.ClassificationBakService;


@Service("classificationBakService")
public class ClassificationBakServiceImpl extends ServiceImpl<ClassificationBakDao, ClassificationBakEntity> implements ClassificationBakService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ClassificationBakEntity> page = this.page(
                new Query<ClassificationBakEntity>().getPage(params),
                new QueryWrapper<ClassificationBakEntity>()
        );

        return new PageUtils(page);
    }

}
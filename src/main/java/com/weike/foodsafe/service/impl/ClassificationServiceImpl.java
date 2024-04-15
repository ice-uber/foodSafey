package com.weike.foodsafe.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.Query;

import com.weike.foodsafe.dao.ClassificationDao;
import com.weike.foodsafe.entity.ClassificationEntity;
import com.weike.foodsafe.service.ClassificationService;


@Service("classificationService")
public class ClassificationServiceImpl extends ServiceImpl<ClassificationDao, ClassificationEntity> implements ClassificationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ClassificationEntity> page = this.page(
                new Query<ClassificationEntity>().getPage(params),
                new QueryWrapper<ClassificationEntity>()
        );

        return new PageUtils(page);
    }

}
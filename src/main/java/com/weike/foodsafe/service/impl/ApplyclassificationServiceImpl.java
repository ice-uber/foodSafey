package com.weike.foodsafe.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.Query;

import com.weike.foodsafe.dao.ApplyclassificationDao;
import com.weike.foodsafe.entity.ApplyclassificationEntity;
import com.weike.foodsafe.service.ApplyclassificationService;


@Service("applyclassificationService")
public class ApplyclassificationServiceImpl extends ServiceImpl<ApplyclassificationDao, ApplyclassificationEntity> implements ApplyclassificationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ApplyclassificationEntity> page = this.page(
                new Query<ApplyclassificationEntity>().getPage(params),
                new QueryWrapper<ApplyclassificationEntity>()
        );

        return new PageUtils(page);
    }

}
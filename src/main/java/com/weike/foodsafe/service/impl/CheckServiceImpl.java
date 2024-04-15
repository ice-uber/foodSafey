package com.weike.foodsafe.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.Query;

import com.weike.foodsafe.dao.CheckDao;
import com.weike.foodsafe.entity.CheckEntity;
import com.weike.foodsafe.service.CheckService;


@Service("checkService")
public class CheckServiceImpl extends ServiceImpl<CheckDao, CheckEntity> implements CheckService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CheckEntity> page = this.page(
                new Query<CheckEntity>().getPage(params),
                new QueryWrapper<CheckEntity>()
        );

        return new PageUtils(page);
    }

}
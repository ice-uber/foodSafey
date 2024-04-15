package com.weike.foodsafe.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.Query;

import com.weike.foodsafe.dao.CheckPDao;
import com.weike.foodsafe.entity.CheckPEntity;
import com.weike.foodsafe.service.CheckPService;


@Service("checkPService")
public class CheckPServiceImpl extends ServiceImpl<CheckPDao, CheckPEntity> implements CheckPService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CheckPEntity> page = this.page(
                new Query<CheckPEntity>().getPage(params),
                new QueryWrapper<CheckPEntity>()
        );

        return new PageUtils(page);
    }

}
package com.weike.foodsafe.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.Query;

import com.weike.foodsafe.dao.VerifyforpwdDao;
import com.weike.foodsafe.entity.VerifyforpwdEntity;
import com.weike.foodsafe.service.VerifyforpwdService;


@Service("verifyforpwdService")
public class VerifyforpwdServiceImpl extends ServiceImpl<VerifyforpwdDao, VerifyforpwdEntity> implements VerifyforpwdService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<VerifyforpwdEntity> page = this.page(
                new Query<VerifyforpwdEntity>().getPage(params),
                new QueryWrapper<VerifyforpwdEntity>()
        );

        return new PageUtils(page);
    }

}
package com.weike.system.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.Query;

import com.weike.system.dao.UserDevDao;
import com.weike.system.entity.UserDevEntity;
import com.weike.system.service.UserDevService;


@Service("userDevService")
public class UserDevServiceImpl extends ServiceImpl<UserDevDao, UserDevEntity> implements UserDevService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserDevEntity> page = this.page(
                new Query<UserDevEntity>().getPage(params),
                new QueryWrapper<UserDevEntity>()
        );

        return new PageUtils(page);
    }

}
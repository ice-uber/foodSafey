package com.weike.system.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.Query;

import com.weike.system.dao.UserRoleDevDao;
import com.weike.system.entity.UserRoleDevEntity;
import com.weike.system.service.UserRoleDevService;


@Service("userRoleDevService")
public class UserRoleDevServiceImpl extends ServiceImpl<UserRoleDevDao, UserRoleDevEntity> implements UserRoleDevService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserRoleDevEntity> page = this.page(
                new Query<UserRoleDevEntity>().getPage(params),
                new QueryWrapper<UserRoleDevEntity>()
        );

        return new PageUtils(page);
    }

}
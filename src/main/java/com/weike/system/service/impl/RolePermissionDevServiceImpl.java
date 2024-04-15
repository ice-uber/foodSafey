package com.weike.system.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.Query;

import com.weike.system.dao.RolePermissionDevDao;
import com.weike.system.entity.RolePermissionDevEntity;
import com.weike.system.service.RolePermissionDevService;


@Service("rolePermissionDevService")
public class RolePermissionDevServiceImpl extends ServiceImpl<RolePermissionDevDao, RolePermissionDevEntity> implements RolePermissionDevService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RolePermissionDevEntity> page = this.page(
                new Query<RolePermissionDevEntity>().getPage(params),
                new QueryWrapper<RolePermissionDevEntity>()
        );

        return new PageUtils(page);
    }

}
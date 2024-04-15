package com.weike.system.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.Query;

import com.weike.system.dao.RoleMenuDevDao;
import com.weike.system.entity.RoleMenuDevEntity;
import com.weike.system.service.RoleMenuDevService;


@Service("roleMenuDevService")
public class RoleMenuDevServiceImpl extends ServiceImpl<RoleMenuDevDao, RoleMenuDevEntity> implements RoleMenuDevService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RoleMenuDevEntity> page = this.page(
                new Query<RoleMenuDevEntity>().getPage(params),
                new QueryWrapper<RoleMenuDevEntity>()
        );

        return new PageUtils(page);
    }

}
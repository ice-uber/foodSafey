package com.weike.system.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.Query;

import com.weike.system.dao.RoleDevDao;
import com.weike.system.entity.RoleDevEntity;
import com.weike.system.service.RoleDevService;


@Service("roleDevService")
public class RoleDevServiceImpl extends ServiceImpl<RoleDevDao, RoleDevEntity> implements RoleDevService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RoleDevEntity> page = this.page(
                new Query<RoleDevEntity>().getPage(params),
                new QueryWrapper<RoleDevEntity>()
        );

        return new PageUtils(page);
    }

}
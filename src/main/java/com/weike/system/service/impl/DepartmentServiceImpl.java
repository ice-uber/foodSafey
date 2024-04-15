package com.weike.system.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.Query;

import com.weike.system.dao.DepartmentDao;
import com.weike.system.entity.DepartmentEntity;
import com.weike.system.service.DepartmentService;


@Service("departmentService")
public class DepartmentServiceImpl extends ServiceImpl<DepartmentDao, DepartmentEntity> implements DepartmentService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DepartmentEntity> page = this.page(
                new Query<DepartmentEntity>().getPage(params),
                new QueryWrapper<DepartmentEntity>()
        );

        return new PageUtils(page);
    }

}
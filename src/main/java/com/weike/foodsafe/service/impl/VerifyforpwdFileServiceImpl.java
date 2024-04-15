package com.weike.foodsafe.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.Query;

import com.weike.foodsafe.dao.VerifyforpwdFileDao;
import com.weike.foodsafe.entity.VerifyforpwdFileEntity;
import com.weike.foodsafe.service.VerifyforpwdFileService;


@Service("verifyforpwdFileService")
public class VerifyforpwdFileServiceImpl extends ServiceImpl<VerifyforpwdFileDao, VerifyforpwdFileEntity> implements VerifyforpwdFileService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<VerifyforpwdFileEntity> page = this.page(
                new Query<VerifyforpwdFileEntity>().getPage(params),
                new QueryWrapper<VerifyforpwdFileEntity>()
        );

        return new PageUtils(page);
    }

}
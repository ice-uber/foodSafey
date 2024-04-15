package com.weike.foodsafe.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.Query;

import com.weike.foodsafe.dao.AttachmentsDao;
import com.weike.foodsafe.entity.AttachmentsEntity;
import com.weike.foodsafe.service.AttachmentsService;


@Service("attachmentsService")
public class AttachmentsServiceImpl extends ServiceImpl<AttachmentsDao, AttachmentsEntity> implements AttachmentsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttachmentsEntity> page = this.page(
                new Query<AttachmentsEntity>().getPage(params),
                new QueryWrapper<AttachmentsEntity>()
        );

        return new PageUtils(page);
    }

}
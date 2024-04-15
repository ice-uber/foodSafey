package com.weike.system.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.Query;

import com.weike.system.dao.StationDao;
import com.weike.system.entity.StationEntity;
import com.weike.system.service.StationService;


@Service("stationService")
public class StationServiceImpl extends ServiceImpl<StationDao, StationEntity> implements StationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StationEntity> page = this.page(
                new Query<StationEntity>().getPage(params),
                new QueryWrapper<StationEntity>()
        );

        return new PageUtils(page);
    }

}
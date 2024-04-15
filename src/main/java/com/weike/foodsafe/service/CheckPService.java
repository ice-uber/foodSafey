package com.weike.foodsafe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weike.common.utils.PageUtils;
import com.weike.foodsafe.entity.CheckPEntity;

import java.util.Map;

/**
 * 
 *
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
public interface CheckPService extends IService<CheckPEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


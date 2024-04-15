package com.weike.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weike.common.utils.PageUtils;
import com.weike.system.entity.ModuleEntity;

import java.util.Map;

/**
 * 菜单分类
 *
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-10 14:03:35
 */
public interface ModuleService extends IService<ModuleEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


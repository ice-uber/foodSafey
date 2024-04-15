package com.weike.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weike.common.utils.PageUtils;
import com.weike.system.entity.LogEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * 系统日志
 *
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-10 14:03:35
 */
public interface LogService extends IService<LogEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void actionRecord(HttpServletRequest request);
}


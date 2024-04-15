package com.weike.foodsafe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weike.common.utils.PageUtils;
import com.weike.foodsafe.entity.VerifyforpwdFileEntity;

import java.util.Map;

/**
 * 密码验证文件
 *
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
public interface VerifyforpwdFileService extends IService<VerifyforpwdFileEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


package com.weike.foodsafe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weike.common.utils.PageUtils;
import com.weike.foodsafe.entity.ClassificationEntity;
import com.weike.foodsafe.vo.goods.CategoryResVo;

import java.util.ArrayList;
import java.util.Map;

/**
 * 分类表
 *
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
public interface ClassificationService extends IService<ClassificationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    ArrayList<CategoryResVo> getCategoryTree();
}


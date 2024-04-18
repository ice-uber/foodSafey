package com.weike.foodsafe.service.impl;

import com.weike.foodsafe.vo.goods.CategoryResVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.Query;

import com.weike.foodsafe.dao.ClassificationDao;
import com.weike.foodsafe.entity.ClassificationEntity;
import com.weike.foodsafe.service.ClassificationService;


@Service("classificationService")
public class ClassificationServiceImpl extends ServiceImpl<ClassificationDao, ClassificationEntity> implements ClassificationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ClassificationEntity> page = this.page(
                new Query<ClassificationEntity>().getPage(params),
                new QueryWrapper<ClassificationEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 获取属性分类数据
     * @return
     */
    @Override
    public ArrayList<CategoryResVo> getCategoryTree() {

        List<ClassificationEntity> categoryList = this.list();
        // 获取所有的一级菜单
        List<ClassificationEntity> classificationLevelOneEntities = categoryList.stream().filter(classificationEntity
                        -> classificationEntity.getParentid().equals("-1"))
                .collect(Collectors.toList());

        //
        ArrayList<CategoryResVo> categoryResVos = new ArrayList<>();

        classificationLevelOneEntities.forEach( classificationEntity -> {
            CategoryResVo categoryResVo = new CategoryResVo();
            List<CategoryResVo> childrenCategory = getChildrenCategory(categoryList, classificationEntity.getClassificationid());
            categoryResVo.setValue(classificationEntity.getClassificationid());
            categoryResVo.setLabel(classificationEntity.getClassificationname());
            categoryResVo.setChildren(childrenCategory);
            categoryResVos.add(categoryResVo);
        });

        return categoryResVos;
    }

    private List<CategoryResVo> getChildrenCategory(List<ClassificationEntity> categoryList , String categoryId) {
        CategoryResVo categoryResVo = new CategoryResVo();
        // 获取当前分类的实体类
        List<ClassificationEntity> collect = categoryList.stream().filter(classificationEntity
                        -> classificationEntity.getClassificationid().equals(categoryId))
                .collect(Collectors.toList());

        ClassificationEntity classificationEntity = collect.get(0);
        categoryResVo.setValue(categoryId);
        categoryResVo.setLabel(classificationEntity.getClassificationname());

        // 查询当前分类下是否还有子分类
        List<CategoryResVo> allChildren = categoryList.stream().filter(category
                        -> category.getParentid().equals(categoryId))
                .map(category -> {
                    CategoryResVo categoryResVo1 = new CategoryResVo();
                    categoryResVo1.setLabel(category.getClassificationname());
                    categoryResVo1.setValue(category.getClassificationid());
                    categoryResVo1.setChildren(getChildrenCategory(categoryList, category.getClassificationid()));
                    return categoryResVo1;
                }).collect(Collectors.toList());

        return allChildren;
    }
}
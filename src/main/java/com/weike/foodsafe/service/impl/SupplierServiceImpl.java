package com.weike.foodsafe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.weike.foodsafe.entity.DistributionEntity;
import com.weike.foodsafe.service.DistributionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.Query;

import com.weike.foodsafe.dao.SupplierDao;
import com.weike.foodsafe.entity.SupplierEntity;
import com.weike.foodsafe.service.SupplierService;


@Service("supplierService")
public class SupplierServiceImpl extends ServiceImpl<SupplierDao, SupplierEntity> implements SupplierService {

    @Autowired
    private DistributionService distributionService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SupplierEntity> page = this.page(
                new Query<SupplierEntity>().getPage(params),
                new QueryWrapper<SupplierEntity>()
        );

        return new PageUtils(page);
    }


    /**
     * 获取当前配送商下的全部供货商信息
     * @param token
     * @return
     */
    @Override
    public List<SupplierEntity> getSupplierByToken(String token) {
        String distributionId = distributionService.getDistributionIdByToken(token);
        List<SupplierEntity> supplierEntities = null;
        if (distributionId != null) {
            supplierEntities = this.list(new LambdaQueryWrapper<SupplierEntity>()
                    .eq(SupplierEntity::getDistributionid, distributionId));
        }
        return supplierEntities;
    }

    /**
     * 获取当前账号下供应商下的分页信息
     * @param token
     * @return
     */
    @Override
    public PageUtils getSupplierPageByToken(Map<String , Object> params , String token) {
        String key = (String) params.get("key");
        String type = (String) params.get("type");
        LambdaQueryWrapper<SupplierEntity> wrapper = new LambdaQueryWrapper<>();

        if (!StringUtils.isBlank(key)) {
            wrapper.like(SupplierEntity :: getSupplierCompanyName , key);
        }

        if (!StringUtils.isBlank(type)) {
            wrapper.eq(SupplierEntity :: getType , type);
        }
        String distributionId = distributionService.getDistributionIdByToken(token);
        List<SupplierEntity> supplierEntities = null;
        IPage<SupplierEntity> page = null;
        if (distributionId != null) {
            page = this.page(
                    new Query<SupplierEntity>().getPage(params),
                    wrapper
            );
        }


        return new PageUtils(page);
    }

    /**
     * 新增供货商
     * @param supplier
     * @param token
     */
    @Override
    public void saveSupplier(SupplierEntity supplier, String token) {
        String distributionIdByToken = distributionService.getDistributionIdByToken(token);
        supplier.setDistributionid(distributionIdByToken);
        this.save(supplier);
    }

}
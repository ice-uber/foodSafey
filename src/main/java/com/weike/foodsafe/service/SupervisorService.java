package com.weike.foodsafe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weike.common.utils.PageUtils;
import com.weike.foodsafe.entity.SupervisorEntity;
import com.weike.foodsafe.entity.SupplierEntity;
import com.weike.foodsafe.vo.OrderVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName: SupervisorService
 * @Author: YuanDing
 * @Date: 2024/4/19 20:16
 * @Description:
 */


public interface SupervisorService extends IService<SupervisorEntity> {
    PageUtils orderList(Map<String, Object> params);
}

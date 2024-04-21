package com.weike.foodsafe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weike.common.constant.OrderConstant;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.Query;
import com.weike.foodsafe.dao.SupervisorDao;
import com.weike.foodsafe.entity.OrderEntity;
import com.weike.foodsafe.entity.SupervisorEntity;
import com.weike.foodsafe.service.OrderService;
import com.weike.foodsafe.service.SupervisorService;
import com.weike.foodsafe.vo.OrderVo;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: SupervisorServiceImpl
 * @Author: YuanDing
 * @Date: 2024/4/19 20:17
 * @Description:
 */

@Service
public class SupervisorServiceImpl extends ServiceImpl<SupervisorDao, SupervisorEntity> implements SupervisorService {

    @Autowired
    private OrderService orderService;

    /**
     * 订单列表
     * @param params
     * @return
     */
    @Override
    public PageUtils orderList(Map<String, Object> params) {
        String key = (String) params.get("key");
        String purchaserId = (String) params.get("purchaserId");
        String startDate = (String) params.get("startDate");
        String endDate = (String) params.get("endDate");
        LambdaQueryWrapper<OrderEntity> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isBlank(purchaserId)) {
            wrapper.eq(OrderEntity :: getPurchaserid , purchaserId);
        }
        
        if (!StringUtils.isBlank(key)) {
            wrapper.like(OrderEntity :: getOrderno , key);
        }
        if (!StringUtils.isBlank(startDate)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date parse = simpleDateFormat.parse(startDate);
                LocalDate date = parse.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                wrapper.ge(OrderEntity::getAddtime, date.atStartOfDay());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        if (!StringUtils.isBlank(endDate)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date parse = simpleDateFormat.parse(endDate);
                LocalDate date = parse.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                wrapper.le(OrderEntity::getAddtime, date.atTime(LocalTime.MAX));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        }

        wrapper.and(w -> {
            w.eq(OrderEntity :: getStatus, "3")
                            .or()
                    .eq(OrderEntity :: getStatus, "4");

        });

        IPage<OrderEntity> page = orderService.page(
                new Query<OrderEntity>().getPage(params),
                wrapper
        );

        PageUtils pageUtils = new PageUtils(page);

        List<OrderEntity> records = page.getRecords();
        List<OrderVo> orderList = orderService.getOrderList(records);
        pageUtils.setList(orderList);

        return pageUtils;

    }
}

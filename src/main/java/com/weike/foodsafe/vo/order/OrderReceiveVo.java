package com.weike.foodsafe.vo.order;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName: OrderReceiveVo
 * @Author: YuanDing
 * @Date: 2024/4/16 18:23
 * @Description:
 */

@Data
public class OrderReceiveVo {

    private String orderid;
    List<OrderDetail> children;

    @Data
    static public class OrderDetail{

        private String orderdetailid;
        private BigDecimal actualamount;
    }
}

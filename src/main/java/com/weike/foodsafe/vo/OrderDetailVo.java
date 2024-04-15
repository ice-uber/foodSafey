package com.weike.foodsafe.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName: OrderDetailVo
 * @Author: YuanDing
 * @Date: 2024/4/13 16:51
 * @Description:
 */

@Data
public class OrderDetailVo {

    private String orderno;

    private String goodsName;

    private String purchaserName;

    /**
     *
     */
    private String orderdetailid;

    /**
     *
     */
    private BigDecimal price;

    private BigDecimal amount;

    private BigDecimal actualamount;

    private BigDecimal totalMoney;

    private Date signtime;

}

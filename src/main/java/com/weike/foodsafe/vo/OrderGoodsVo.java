package com.weike.foodsafe.vo;

import lombok.Data;

/**
 * @ClassName: OrderGoodsVo
 * @Author: YuanDing
 * @Date: 2024/4/12 23:44
 * @Description:
 */

@Data
public class OrderGoodsVo {
    private String orderdetailid;

    private String goodsName;

    private Boolean hasInputSource;

}

package com.weike.foodsafe.vo.shoppingCart;

import lombok.Data;

/**
 * @ClassName: ShoppingCartVo
 * @Author: YuanDing
 * @Date: 2024/4/15 9:17
 * @Description:
 */

@Data
public class ShoppingCartVo {
    private String distributionId;
    private Integer amount;
    private String goodsId;
}

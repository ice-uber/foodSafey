package com.weike.foodsafe.vo.shoppingCart;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName: ShoppingCartResVo
 * @Author: YuanDing
 * @Date: 2024/4/15 10:30
 * @Description:
 */

@Data
public class ShoppingCartResVo {

    private String goodsid;
    /**
     *
     */
    private String classificationid;
    /**
     *
     */
    private String goodsname;
    /**
     *
     */
    private String goodsno;
    /**
     *
     */
    private String goodsimg;
    /**
     *
     */
    private String imgurl;
    /**
     *
     */
    private String goodsunit;
    /**
     *
     */
    private String distributionid;
    /**
     *
     */
    private Date addtime;
    /**
     * 0上架1下架
     */
    private String status;
    /**
     *
     */
    private BigDecimal price;
    /**
     *
     */
    private BigDecimal minamount;

    private Integer number;
}

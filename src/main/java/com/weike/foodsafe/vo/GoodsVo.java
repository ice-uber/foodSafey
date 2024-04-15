package com.weike.foodsafe.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName: GoodsVo
 * @Author: YuanDing
 * @Date: 2024/4/11 18:48
 * @Description:
 */

@Data
public class GoodsVo {
    @TableId
    private String goodsid;
    /**
     *
     */
    private String categoryName;
    /**
     *
     */
    private String goodsname;

    /**
     *
     */
    private String goodsimg;

    /**
     *
     */
    private String goodsunit;

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
}

package com.weike.common.to;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName: SpuBoundsTo
 * @Author: YuanDing
 * @Date: 2024/4/2 10:34
 * @Description:
 */

@Data

public class SpuBoundsTo {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     *
     */
    private Long spuId;
    /**
     * 成长积分
     */
    private BigDecimal growBounds;
    /**
     * 购物积分
     */
    private BigDecimal buyBounds;
    /**
     * 优惠生效情况[1111（四个状态位，从右到左）;0 - 无优惠，成长积分是否赠送;1 - 无优惠，购物积分是否赠送;2 - 有优惠，成长积分是否赠送;3 - 有优惠，购物积分是否赠送【状态位0：不赠送，1：赠送】]
     */
    private Integer work;
}

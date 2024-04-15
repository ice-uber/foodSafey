package com.weike.common.to;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName: SkuReductionTo
 * @Author: YuanDing
 * @Date: 2024/4/2 13:40
 * @Description:
 */

@Data
public class SkuReductionTo {

    private Long skuId;
    private BigDecimal fullCount;
    private BigDecimal discount;
    private BigDecimal fullPrice;
    private BigDecimal reducePrice;
    private List<MemberPrice> memberPrice;

}

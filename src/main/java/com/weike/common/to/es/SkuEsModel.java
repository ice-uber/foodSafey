package com.weike.common.to.es;


import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName: SkuEsModel
 * @Author: YuanDing
 * @Date: 2024/4/7 14:02
 * @Description:
 */

@Data
public class SkuEsModel {

    private Long SkuId;
    private Long SpuId;
    private String skuTitle;
    private BigDecimal skuPrice;
    private String skuImg;
    private Long saleCount;
    private Boolean hasStock;
    private Long hotScore;
    private Long brandId;
    private String brandName;
    private String brandImg;

    private List<Attr> attrs;

    @Data
    static public class Attr{
         private Long attrId;
         private String attrName;
         private String attrValue;
    }

}

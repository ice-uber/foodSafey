package com.weike.foodsafe.vo.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: OrderCheckOutVo
 * @Author: YuanDing
 * @Date: 2024/4/15 8:12
 * @Description:
 */


@Data
public class OrderCheckOutVo {

    private String distributionId;

    private String message;

    private List<Goods> goodsList;

    private String timerange;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date distributiondate;

    private String addressId;

    @Data
    static public class Goods {
        private String goodsid;

        private Integer number;
    }
}

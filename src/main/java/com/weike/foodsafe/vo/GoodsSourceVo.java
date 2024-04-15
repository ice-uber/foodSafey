package com.weike.foodsafe.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: GoodsSourceVo
 * @Author: YuanDing
 * @Date: 2024/4/12 21:25
 * @Description:
 */

@Data
public class GoodsSourceVo {

    private List<String> orderIdList;
    private String supplierId;
    private String pzurl;
    private String orderId;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date purchaseTime;
    private List<String> orderDetailIds;

}

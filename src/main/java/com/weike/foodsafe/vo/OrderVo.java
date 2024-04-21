package com.weike.foodsafe.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.weike.foodsafe.entity.SupplierEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: OrderVo
 * @Author: YuanDing
 * @Date: 2024/4/12 10:02
 * @Description:
 */

@Data
public class OrderVo {
    private String orderid;
    /**
     *
     */
    private String orderno;
    /**
     *
     */
    private String addrid;
    /**
     *
     */
    private BigDecimal money;
    /**
     *
     */
    private BigDecimal actualmoney;
    /**
     *
     */
    private Date addtime;
    /**
     * 时间段
     */
    private String timerange;
    /**
     * 配送日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date distributiondate;
    /**
     * 配送商ID
     */
    private String distributionid;

    private String distributionCompanyName;

    private List<String> distributionPzUrl;
    private List<String> purchaserPzUrl;
    /**
     *
     */
    private String status;
    /**
     * 采购用户id
     */
    private String adduser;
    /**
     *
     */
    private String purchaserid;

    private String purchaserName;

    /**
     *
     */
    private Date updatetime;
    /**
     * 收货人
     */
    private String finishuser;
    /**
     * 确认收货时间
     */
    private Date finishtime;
    /**
     * 签收人
     */
    private String signuser;
    /**
     * 签收时间
     */
    private Date signtime;
    /**
     *
     */
    private String confirmsignuser;
    /**
     *
     */
    private Date confirmsigntime;
    /**
     *
     */
    private String address;
    /**
     *
     */
    private String addrName;

    private String distributionAddr;
    private String distributionPerson;
    private String distributionTel;
    /**
     *
     */
    private String addrPhone;
    /**
     *
     */
    private String addrArea;

    private List<OrderDetailVo> children;



    @Data
    static public class OrderDetailVo{
        private SupplierEntity supplierEntity;

        @JsonFormat(pattern = "yyyy-MM-dd")
        private Date purchaseTime;
        private String goodsName;

        private String orderdetailid;
        /**
         *
         */
        private String goodsid;
        /**
         *
         */
        private String classificationid;
        /**
         *
         */
        private String classificationname;
        /**
         *
         */
        private BigDecimal price;
        /**
         * 商品图片
         */
        private String imgurl;

        /**
         *
         */
        private String goodsunit;
        /**
         *
         */
        private BigDecimal amount;
        /**
         *
         */
        private BigDecimal actualamount;
        /**
         *
         */
        private String orderid;
        /**
         * 供货商ID
         */
        private String supplierid;
        /**
         * 凭证图片
         */
        private String pzurl;
        /**
         * 留言
         */
        private String message;

    }
}

package com.weike.foodsafe.vo;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName: cooperationResVo
 * @Author: YuanDing
 * @Date: 2024/4/15 22:36
 * @Description:
 */

@Data
public class CooperationResVo {
    private String purchaserid;
    /**
     *
     */
    private String companyname;

    private String userId;

    private String addr;
    /**
     *
     */
    private String account;
    /**
     *
     */
    private String name;
    /**
     *
     */
    private String phone;
    /**
     *
     */
    private String email;

    private String cooperationid;
    /**
     *
     */
    private String distributionid;

    /**
     *
     */
    private Date begindate;
    /**
     *
     */
    private Date enddate;
    /**
     *
     */
    private String status;
    /**
     *
     */
    private String islongrelation;


}

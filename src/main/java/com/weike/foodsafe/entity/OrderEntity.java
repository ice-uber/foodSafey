package com.weike.foodsafe.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
@Data
@TableName("ybsx_order")
public class OrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
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
	@TableField(fill = FieldFill.INSERT)
	private Date addtime;
	/**
	 * 时间段
	 */
	private String timerange;
	/**
	 * 配送日期
	 */
	private Date distributiondate;
	/**
	 * 配送商ID
	 */
	private String distributionid;
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
	/**
	 * 
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
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
	/**
	 * 
	 */
	private String addrPhone;
	/**
	 * 
	 */
	private String addrArea;

}

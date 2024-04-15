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
@TableName("ybsx_shoppingcar")
public class ShoppingcarEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String carid;
	/**
	 * 
	 */
	private String purchaserid;
	/**
	 * 
	 */
	private String madegoodsid;
	/**
	 * 
	 */
	private BigDecimal amount;
	/**
	 * 
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date addtime;
	/**
	 * 添加用户id
	 */
	private String adduser;
	private String distributionId;



}

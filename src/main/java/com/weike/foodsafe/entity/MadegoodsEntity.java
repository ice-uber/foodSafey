package com.weike.foodsafe.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 食品来源表
 * 
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
@Data
@TableName("ybsx_madegoods")
public class MadegoodsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String madegoodsid;
	/**
	 * 
	 */
	private String purchaserid;
	/**
	 * 
	 */
	private String distributionid;
	/**
	 * 
	 */
	private String goodsid;
	/**
	 * 
	 */
	private BigDecimal price;
	/**
	 * 
	 */
	private Date addtime;

}

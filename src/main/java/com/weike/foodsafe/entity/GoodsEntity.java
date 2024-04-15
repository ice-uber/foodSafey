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
 * 商品表
 * 
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
@Data
@TableName("ybsx_goods")
public class GoodsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String goodsid;
	/**
	 * 
	 */
	private String classificationid;
	/**
	 * 
	 */
	private String goodsname;
	/**
	 * 
	 */
	private String goodsno;
	/**
	 * 
	 */
	private String goodsimg;
	/**
	 * 
	 */
	private String imgurl;
	/**
	 * 
	 */
	private String goodsunit;
	/**
	 * 
	 */
	private String distributionid;
	/**
	 * 
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date addtime;
	/**
	 * 0上架1下架
	 */
	private String status;
	/**
	 * 
	 */
	private BigDecimal price;
	/**
	 * 
	 */
	private BigDecimal minamount;

}

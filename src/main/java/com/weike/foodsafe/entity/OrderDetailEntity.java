package com.weike.foodsafe.entity;

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
@TableName("ybsx_order_detail")
public class OrderDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
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

	/**
	 * 进货日期
	 */
	private Date purchaseTime;

}

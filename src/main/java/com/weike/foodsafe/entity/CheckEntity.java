package com.weike.foodsafe.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 检测表
 * 
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
@Data
@TableName("ybsx_check")
public class CheckEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String checkid;
	/**
	 * 
	 */
	private String orderdetailid;
	/**
	 * 
	 */
	private String checkResult;
	/**
	 * 检测时间
	 */
	private Date addtime;
	/**
	 * 
	 */
	private String checkType;
	/**
	 * 
	 */
	private String checkItem;
	/**
	 * 
	 */
	private BigDecimal checkStandardval;
	/**
	 * 
	 */
	private BigDecimal checkTestval;
	/**
	 * 检测人
	 */
	private String adduser;
	/**
	 * 1采购商检测2配送商检测
	 */
	private String type;
	/**
	 * 配送商/采购商Id
	 */
	private String otherId;
	/**
	 * 
	 */
	private String classificationid;

}

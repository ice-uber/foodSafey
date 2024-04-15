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
@TableName("ybsx_check_p")
public class CheckPEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 
	 */
	private String orderdetailid;
	/**
	 * 
	 */
	private String classificationid;
	/**
	 * 
	 */
	private Date addtime;
	/**
	 * 
	 */
	private String adduser;
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
	 * 
	 */
	private String checkResult;

}

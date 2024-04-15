package com.weike.foodsafe.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 合作表
 * 
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
@Data
@TableName("ybsx_cooperation")
public class CooperationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String cooperationid;
	/**
	 * 
	 */
	private String distributionid;
	/**
	 * 
	 */
	private String purchaserid;
	/**
	 * 
	 */
	private Date addtime;
	/**
	 * 
	 */
	private Date updatetime;
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

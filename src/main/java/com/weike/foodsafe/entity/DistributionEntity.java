package com.weike.foodsafe.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 分配表
 * 
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
@Data
@TableName("ybsx_distribution")
public class DistributionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String distributionid;
	/**
	 * 
	 */
	private String companyname;
	/**
	 * 
	 */
	private String userId;
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
	/**
	 * 
	 */
	private String area;
	/**
	 * 
	 */
	private String addr;
	/**
	 * 1启动检测
	 */
	private String checkEnable;

	private String hasAuthentication;
	private String shopImg;

}

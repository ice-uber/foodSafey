package com.weike.foodsafe.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

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
@TableName("ybsx_applyclassification")
public class ApplyclassificationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String applyid;
	/**
	 * 
	 */
	private String distributionid;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String parentid;
	/**
	 * 
	 */
	private String imgurl;
	/**
	 * 0未审核1通过2未通过
	 */
	private String status;
	/**
	 * 
	 */
	private Date addtime;
	/**
	 * 录入人ID
	 */
	private String inputuser;

}

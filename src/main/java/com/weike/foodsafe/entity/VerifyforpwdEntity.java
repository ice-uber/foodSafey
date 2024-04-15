package com.weike.foodsafe.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 密码验证
 * 
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
@Data
@TableName("ybsx_verifyforpwd")
public class VerifyforpwdEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String verifyid;
	/**
	 * 
	 */
	private String account;
	/**
	 * 
	 */
	private String companyname;
	/**
	 * 
	 */
	private String phone;
	/**
	 * 
	 */
	private Date addtime;
	/**
	 * 0未审核1通过2未通过
	 */
	private Integer status;

}

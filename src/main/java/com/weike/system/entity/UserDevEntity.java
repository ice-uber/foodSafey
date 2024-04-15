package com.weike.system.entity;

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
 * @date 2024-04-10 14:03:35
 */
@Data
@TableName("sys_user_dev")
public class UserDevEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String userId;
	/**
	 * 
	 */
	private String stationId;
	/**
	 * 
	 */
	private String deptId;
	/**
	 * 
	 */
	private String userName;
	/**
	 * 
	 */
	private String userPassword;
	/**
	 * 
	 */
	private String userRole;
	/**
	 * 
	 */
	private String userRealname;
	/**
	 * 
	 */
	private String userCard;
	/**
	 * 
	 */
	private Integer userIsEnable;
	/**
	 * 
	 */
	private Date userInputtime;
	/**
	 * 
	 */
	private String userLastIp;
	/**
	 * 
	 */
	private String userNo;
	/**
	 * 
	 */
	private Date userLastTime;
	/**
	 * 
	 */
	private String userTel;
	/**
	 * 用户类型0是普通开发用户1是超级开发管理员用户
	 */
	private Integer userAccounttype;

}

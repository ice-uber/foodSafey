package com.weike.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户表
 * 
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-10 14:03:35
 */
@Data
@TableName("sys_user")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String userId;
	/**
	 * 职位id
	 */
	private String stationId;
	/**
	 * 部门id
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
	 * 用户是否启用 0-未启用 1-启用
	 */
	private Integer userIsEnable;
	/**
	 * 
	 */
	private Date userInputtime;
	/**
	 * 用户最后一次登录的ip地址
	 */
	private String userLastIp;
	/**
	 * 
	 */
	private String userNo;
	/**
	 * 用户最后一次登录的时间
	 */
	private Date userLastTime;
	/**
	 * 电话号码
	 */
	private String userTel;
	/**
	 * 用户类型0是普通用户1是开发管理员用户
	 */
	private Integer userAccounttype;
	/**
	 * 
	 */
	private Integer schoolId;
	/**
	 * 录入人所在部门
	 */
	private String inputuserdept;
	/**
	 * 邮箱
	 */
	private String userEmail;
	/**
	 * 用户类型1采购商2配送商3采购商子账号4配送商子账号0其他
	 */
	private String userType;
	/**
	 * 关联ID：配送商ID或者收购商ID
	 */
	private String otherId;
	/**
	 * 子账号所属配送商或者采购商的ID
	 */
	private String belongId;
	/**
	 * 审核状态（采购商、配送商）0未审核1通过2不通过
	 */
	private String auditstatus;

	/**
	 * 用户头像
	 */
	private String avatar;

}

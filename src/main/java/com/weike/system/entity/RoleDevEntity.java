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
@TableName("sys_role_dev")
public class RoleDevEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String roleId;
	/**
	 * 
	 */
	private String roleName;
	/**
	 * 1、超级管理员
            2、普通管理员
            默认是普通管理员
            
	 */
	private String roleType;
	/**
	 * 来自组织机构，在此就不关联了
	 */
	private String roleDept;
	/**
	 * 
	 */
	private String roleOther;

}

package com.weike.foodsafe.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 密码验证文件
 * 
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
@Data
@TableName("ybsx_verifyforpwd_file")
public class VerifyforpwdFileEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String fileid;
	/**
	 * 
	 */
	private String url;
	/**
	 * 
	 */
	private String realname;
	/**
	 * 
	 */
	private String verifyid;
	/**
	 *  配送商 1营业证件,2法人身份证3法人授权书
	 */
	private String type;

}

package com.weike.foodsafe.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 附件表
 * 
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
@Data
@TableName("ybsx_attachments")
public class AttachmentsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String attachmentid;
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
	private String relationid;
	/**
	 *  配送商 1营业证件,2法人身份证建采购商 3营业证件,4法人身份证 5健康证 6餐饮服务证  
	 */
	private String type;

}

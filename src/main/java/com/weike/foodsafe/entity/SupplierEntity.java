package com.weike.foodsafe.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 供货商表
 * 
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
@Data
@TableName("ybsx_supplier")
public class SupplierEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String supplierid;
	/**
	 * 
	 */
	private String distributionid;

	private String supplierCompanyName;
	/**
	 * 
	 */
	private String type;
	/**
	 * 
	 */
	private String suppliername;
	/**
	 * 
	 */
	private String supplieraddress;
	/**
	 * 
	 */
	private String fzr;
	/**
	 * 
	 */
	private String phone;
	/**
	 * 
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date addtime;
	/**
	 * 
	 */
	private String status;
	/**
	 * 
	 */
	private String area;

}

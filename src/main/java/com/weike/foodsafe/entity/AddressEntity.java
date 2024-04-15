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
@TableName("ybsx_address")
public class AddressEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String addressid;
	/**
	 * 
	 */
	private String address;
	/**
	 * 
	 */
	private String purchaserid;
	/**
	 * 1默认地址
	 */
	private String isdefault;
	/**
	 * 
	 */
	private String remark;
	/**
	 * 
	 */
	private String area;
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
	private Date addtime;
	/**
	 * 删除标记为 1删除
	 */
	private String delflag;

}

package com.weike.foodsafe.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 分类表
 * 
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-09 17:00:47
 */
@Data
@TableName("ybsx_classification")
public class ClassificationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String classificationid;
	/**
	 * 
	 */
	private String classificationname;
	/**
	 * 
	 */
	private String parentid;
	/**
	 * 
	 */
	private Integer sort;
	/**
	 * 
	 */
	private String imgurl;

}

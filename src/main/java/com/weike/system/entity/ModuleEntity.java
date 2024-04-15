package com.weike.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 菜单分类
 * 
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-10 14:03:35
 */
@Data
@TableName("sys_module")
public class ModuleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String moduleId;
	/**
	 * 
	 */
	private String moduleName;
	/**
	 * 是一级菜单还是二级菜单(也是是否为叶子节点)
	 */
	private Integer moduleLevel;
	/**
	 * 
	 */
	private String moduleUrl;
	/**
	 * 
	 */
	private String moduleParentId;
	/**
	 * 
	 */
	private Integer moduleOrder;
	/**
	 * 
	 */
	private String moduleIcon;
	/**
	 * 
	 */
	private Integer moduleIsDisplay;
	/**
	 * 
	 */
	private String moduleOther;

}

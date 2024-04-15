package com.weike.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 系统菜单
 * 
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-10 14:03:35
 */
@Data
@TableName("sys_menu")
public class MenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String menuId;
	/**
	 * 
	 */
	private String moduleId;
	/**
	 * 
	 */
	private String menuName;
	/**
	 * 
	 */
	private Integer menuLevel;
	/**
	 * 
	 */
	private String menuUrl;
	/**
	 * 
	 */
	private String menuParentId;
	/**
	 * 
	 */
	private Integer menuOrder;
	/**
	 * 
	 */
	private String menuIcon;
	/**
	 * 0显示1不显示
	 */
	private Integer menuIsDisplay;
	/**
	 * 
	 */
	private String menuOther;
	/**
	 * 
	 */
	private String menuType;
	/**
	 * 
	 */
	private String menuBigIcon;

}

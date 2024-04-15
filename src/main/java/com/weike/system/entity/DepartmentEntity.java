package com.weike.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 地图表
 * 
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-10 14:03:35
 */
@Data
@TableName("sys_department")
public class DepartmentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 地点id
	 */
	@TableId
	private String deptId;
	/**
	 * 地点名称
	 */
	private String deptName;
	/**
	 * 地点父id
	 */
	private String deptParentId;
	/**
	 * 
	 */
	private String deptIsLeaf;
	/**
	 * 
	 */
	private Integer deptOrder;
	/**
	 * 地方级别 0-省 1-市 2-区
	 */
	private String deptOther;

}

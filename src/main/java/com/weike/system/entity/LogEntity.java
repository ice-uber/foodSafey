package com.weike.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 系统日志
 * 
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-10 14:03:35
 */
@Data
@TableName("sys_log")
public class LogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 日志id
	 */
	@TableId
	private String logId;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 日志标题
	 */
	private String logTitle;
	/**
	 * 日志时间
	 */
	private Date addtime;
	/**
	 * 日志类型 INFO-正常事件 ERROR-异常事件
	 */
	private String logType;
	/**
	 * 请求uri
	 */
	private String reqUri;
	/**
	 * 请求ip
	 */
	private String reqIp;
	/**
	 * 异常代码
	 */
	private String exceptionCode;
	/**
	 * 异常详细信息
	 */
	private String exceptionDetail;
	/**
	 * 参数
	 */
	private String params;
	/**
	 * 请求浏览器
	 */
	private String browser;

}

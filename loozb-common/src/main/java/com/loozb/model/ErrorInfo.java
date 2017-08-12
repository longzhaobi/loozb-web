package com.loozb.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.loozb.core.base.BaseModel;


/**
 * <p>
 * 用户角色信息表
 * </p>
 *
 * @author LongZhaoBi
 * @since 2017-08-13
 */
@TableName("sys_error_info")
@SuppressWarnings("serial")
public class ErrorInfo extends BaseModel {

    /**
     * 异常URL
     */
	private String url;
    /**
     * 异常实例
     */
	private String instance;
    /**
     * 异常信息
     */
	private String exception;
    /**
     * 请求方法
     */
	private String method;
    /**
     * 异常标识
     */
	private String uuid;
    /**
     * 状态吗
     */
	private String status;
    /**
     * 客户端信息
     */
	private String agent;
    /**
     * 发生异常的用户
     */
	private String name;
	@TableField("user_id")
	private Long userId;
    /**
     * 请求IP
     */
	private String ip;


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
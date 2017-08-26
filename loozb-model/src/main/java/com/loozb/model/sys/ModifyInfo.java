package com.loozb.model.sys;

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
@TableName("sys_modify_info")
@SuppressWarnings("serial")
public class ModifyInfo extends BaseModel {

    /**
     * 异常URL
     */
	private String url;
    /**
     * 表名
     */
	@TableField("table_name")
	private String tableName;
    /**
     * 修改信息
     */
	private String content;
	/**
	 * 表信息
	 */
	@TableField("table_info")
	private String tableInfo;
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

	/**
	 * 目标ID
	 */
	private Long key;


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getTableInfo() {
		return tableInfo;
	}

	public void setTableInfo(String tableInfo) {
		this.tableInfo = tableInfo;
	}
}
package com.loozb.model;

/**
 * redis key-value
 * redis的key值为：表名 + ":" + 主键ID
 * @author LONGZB
 *
 */
public class RedisKeyValue {

	private String key;
	private String value;
	
	private String name;
	
	public RedisKeyValue(String key, String value, String name) {
		this.key = key;
		this.value = value;
		this.name = name;
	}
 	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

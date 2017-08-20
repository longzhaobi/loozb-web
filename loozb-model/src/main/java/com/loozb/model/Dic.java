package com.loozb.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.loozb.core.base.BaseModel;


/**
 * <p>
 * 
 * </p>
 *
 * @author 龙召碧
 * @since 2017-07-10
 */
@TableName("sys_dic")
@SuppressWarnings("serial")
public class Dic extends BaseModel {

    /**
     * 名称
     */
	private String name;
    /**
     * 值
     */
	private String value;
	private String code;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
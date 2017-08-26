package com.loozb.model.sys;

import com.baomidou.mybatisplus.annotations.TableName;
import com.loozb.core.base.BaseModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * <p>
 * 用户角色信息表
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-26
 */
@TableName("sys_role")
public class SysRole extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 角色标识
     */
    @NotNull
	@Size(min=2, max = 10, message="[角色标识个数必须在2到10位]")
	private String role;
    /**
     * 角色名称
     */
	@NotNull
	@Size(min=2, max = 10, message="[角色名称个数必须在2到10位]")
	private String name;
    /**
     * 角色描述
     */
	private String description;


	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

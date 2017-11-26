package com.loozb.model.sys;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.loozb.core.base.BaseModel;

import java.util.List;


/**
 * <p>
 * 组织机构部门信息表
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-26
 */
@TableName("sys_organ")
public class SysOrgan extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 机构父编码
     */
	private Long pid;
    /**
     * 机构名称
     */
	private String name;

	private int weight;
    /**
     * 父子链
     */
	private String pids;
    /**
     * 描述
     */
	private String description;

	//扩展
	@TableField(exist = false)
	private List<SysOrgan> children;
	@TableField(exist = false)
	private Boolean leaf = true;
	@TableField(exist = false)
	private String title;

	public String getTitle() {
		return name;
	}


	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPids() {
		return pids;
	}

	public void setPids(String pids) {
		this.pids = pids;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public List<SysOrgan> getChildren() {
		return children;
	}

	public void setChildren(List<SysOrgan> children) {
		this.children = children;
	}

	public Boolean getLeaf() {
		return leaf;
	}
	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
}

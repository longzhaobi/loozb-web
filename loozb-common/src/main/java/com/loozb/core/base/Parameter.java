package com.loozb.core.base;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author ShenHuaJie
 * @since 2017年2月14日 下午3:27:17
 */
@SuppressWarnings("serial")
public class Parameter implements Serializable {
	public Parameter() {
	}

	public Parameter(String service, String method) {
		this.service = service;
		this.method = method;
	}

	public Parameter(Object result) {
		if (result instanceof Long) {
			this.id = (Long) result;
		} else if (result instanceof BaseModel) {
			this.model = (BaseModel) result;
		} else if (result instanceof Page) {
			this.page = (Page<?>) result;
		} else if (result instanceof Map<?, ?>) {
			this.map = (Map<?, ?>) result;
		} else if (result instanceof List<?>) {
			this.list = (List<?>) result;
		} else if (result instanceof Set<?>) {
			this.set = (Set<?>) result;
		}
	}

	private String service;
	private String method;

	private Long id;
	private BaseModel model;
	private Map<?, ?> map;
	private Page<?> page;
	private List<?> list;
	private Set<?> set;

    private final String no = "[" + IdWorker.getId() + "]";

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getMethod() {
		return method;
	}

	public Parameter setMethod(String method) {
		this.method = method;
		return this;
	}

	public Long getId() {
		return id;
	}

	public Parameter setId(Long id) {
		this.id = id;
		return this;
	}

	public BaseModel getModel() {
		return model;
	}

	public Parameter setModel(BaseModel model) {
		this.model = model;
		return this;
	}

	public Map<?, ?> getMap() {
		return map;
	}

	public Parameter setMap(Map<?, ?> map) {
		this.map = map;
		return this;
	}

	public Page<?> getPage() {
		return page;
	}

	public Parameter setPage(Page<?> page) {
		this.page = page;
		return this;
	}

	public List<?> getList() {
		return list;
	}

	public Parameter setList(List<?> list) {
		this.list = list;
		return this;
	}

	public Set<?> getSet() {
		return set;
	}

	public void setSet(Set<?> set) {
		this.set = set;
	}

	public String getNo() {
        return no;
    }
}

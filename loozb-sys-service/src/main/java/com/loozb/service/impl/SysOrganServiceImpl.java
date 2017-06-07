package com.loozb.service.impl;

import com.loozb.core.base.BaseServiceImpl;
import com.loozb.model.SysOrgan;
import com.loozb.service.SysOrganService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 组织机构部门信息表 服务类
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-26
 */
@Service
@CacheConfig(cacheNames = "SysOrgan")
public class SysOrganServiceImpl extends BaseServiceImpl<SysOrgan> implements SysOrganService{
	
}

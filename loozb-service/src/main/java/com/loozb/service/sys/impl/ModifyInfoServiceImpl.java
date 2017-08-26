package com.loozb.service.sys.impl;

import com.loozb.core.base.BaseServiceImpl;
import com.loozb.model.sys.ModifyInfo;
import com.loozb.service.sys.ModifyInfoService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色信息表  服务实现类
 * </p>
 *
 * @author LongZhaoBi
 * @since 2017-08-13
 */
@Service
@CacheConfig(cacheNames = "SysModifyInfo")
public class ModifyInfoServiceImpl extends BaseServiceImpl<ModifyInfo> implements ModifyInfoService {
	
}
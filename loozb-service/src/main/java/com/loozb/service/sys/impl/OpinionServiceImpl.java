package com.loozb.service.sys.impl;

import com.loozb.core.base.BaseService;
import com.loozb.core.base.BaseServiceImpl;
import com.loozb.model.sys.Opinion;
import com.loozb.service.sys.OpinionService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限信息  服务实现类
 * </p>
 *
 * @author LongZhaoBi
 * @since 2017-11-19
 */
@Service
@CacheConfig(cacheNames = "Opinion")
public class OpinionServiceImpl extends BaseServiceImpl<Opinion> implements OpinionService {
	
}
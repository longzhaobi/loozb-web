package com.loozb.service.blog.impl;

import com.loozb.core.base.BaseService;
import com.loozb.core.base.BaseServiceImpl;
import com.loozb.model.blog.Message;
import com.loozb.service.blog.MessageService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 留言 服务类
 * </p>
 *
 * @author 龙召碧
 * @since 2017-03-27
 */
@Service
@CacheConfig(cacheNames = "TbMessage")
public class MessageServiceImpl extends BaseServiceImpl<Message> implements MessageService {
	
}

package com.loozb.service.blog;

import com.loozb.core.base.BaseService;
import com.loozb.model.blog.Message;
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
public interface MessageService extends BaseService<Message> {
	
}

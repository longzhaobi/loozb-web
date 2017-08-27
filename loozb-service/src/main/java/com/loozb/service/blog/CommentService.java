package com.loozb.service.blog;

import com.loozb.core.base.BaseService;
import com.loozb.model.blog.Comment;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author 龙召碧
 * @since 2017-03-27
 */
public interface CommentService extends BaseService<Comment> {

}

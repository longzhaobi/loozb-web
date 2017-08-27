package com.loozb.service.blog.impl;

import com.loozb.core.base.BaseService;
import com.loozb.core.base.BaseServiceImpl;
import com.loozb.model.blog.Comment;
import com.loozb.service.blog.CommentService;
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
@Service
@CacheConfig(cacheNames = "TbComment")
public class CommentServiceImpl extends BaseServiceImpl<Comment> implements CommentService{

}

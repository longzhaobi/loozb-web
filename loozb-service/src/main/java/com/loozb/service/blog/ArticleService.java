package com.loozb.service.blog;

import com.loozb.core.base.BaseService;
import com.loozb.model.blog.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 博客列表 服务类
 * </p>
 *
 * @author 龙召碧
 * @since 2017-03-22
 */
public interface ArticleService extends BaseService<Article> {
}

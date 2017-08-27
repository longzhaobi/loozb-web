package com.loozb.service.blog.impl;

import com.loozb.core.base.BaseService;
import com.loozb.core.base.BaseServiceImpl;
import com.loozb.model.blog.Article;
import com.loozb.model.blog.Classification;
import com.loozb.service.blog.ArticleService;
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
@Service
@CacheConfig(cacheNames = "TbArticle")
public class ArticleServiceImpl extends BaseServiceImpl<Article> implements ArticleService {
    @Autowired
    private ClassificationServiceImpl classificationService;

    @Override
    public Article queryById(Long id) {
        Article article = super.queryById(id);
        if(article != null) {
            Classification classification = classificationService.queryById(Long.parseLong(article.getClassification()));
            if(classification != null) {
                article.setClassificationName(classification.getName());
            }
        }
        return article;
    }
}

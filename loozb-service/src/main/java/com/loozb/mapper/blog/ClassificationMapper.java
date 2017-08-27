package com.loozb.mapper.blog;

import com.loozb.core.base.BaseMapper;
import com.loozb.model.blog.Classification;

/**
 * <p>
  * 文章分类 Mapper 接口
 * </p>
 *
 * @author 龙召碧
 * @since 2017-03-25
 */
public interface ClassificationMapper extends BaseMapper<Classification> {

    int queryArticleCount(String id);
}
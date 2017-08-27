package com.loozb.service.blog;

import com.loozb.core.base.BaseService;
import com.loozb.model.blog.Classification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文章分类 服务类
 * </p>
 *
 * @author 龙召碧
 * @since 2017-03-25
 */
public interface ClassificationService extends BaseService<Classification> {
}

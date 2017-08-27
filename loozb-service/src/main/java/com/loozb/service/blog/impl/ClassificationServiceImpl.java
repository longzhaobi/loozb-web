package com.loozb.service.blog.impl;

import com.loozb.core.base.BaseService;
import com.loozb.core.base.BaseServiceImpl;
import com.loozb.mapper.blog.ClassificationMapper;
import com.loozb.model.blog.Classification;
import com.loozb.service.blog.ClassificationService;
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
@Service
@CacheConfig(cacheNames = "TbClassification")
public class ClassificationServiceImpl extends BaseServiceImpl<Classification> implements ClassificationService {
    @Autowired(required = false)
    private ClassificationMapper classificationMapper;

    @Override
    public List<Classification> queryList(Map<String, Object> params) {
        List<Classification> list = super.queryList(params);
        List<Classification> myList = new ArrayList<Classification>();
        for (int i = 0; i < list.size(); i++) {
            Classification tc = list.get(i);
            int count = classificationMapper.queryArticleCount(tc.getId().toString());
            tc.setArticleNum(count);
            myList.add(tc);
        }
        return myList;
    }
}

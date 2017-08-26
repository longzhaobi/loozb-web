package com.loozb.service.sys;

import org.springframework.ui.ModelMap;

/**
 * <p>
 *   服务实现类
 * </p>
 *
 * @author 龙召碧
 * @since 2017-07-10
 */
public interface CacheService{
    Object query(ModelMap modelMap, Integer current, Integer size, String keyword, String tableName);

    void removeCurrentCache(String table);
}
package com.loozb.service.sys.impl;

import com.loozb.core.Constants;
import com.loozb.core.util.CacheUtil;
import com.loozb.core.util.JsonUtils;
import com.loozb.core.util.ParamUtil;
import com.loozb.mapper.sys.SysTableMapper;
import com.loozb.model.sys.SysTable;
import com.loozb.service.sys.SysTableService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-25
 */
@Service
@CacheConfig(cacheNames = "SysTable")
public class SysTableServiceImpl implements SysTableService {

    @Autowired(required = false)
    private SysTableMapper sysTableMapper;
    /**
     * 根据表明获取表字段和注释
     *
     * @param tableName
     * @return
     */
    @Override
    public List<SysTable> selectTableByName(String tableName) {
        Map<String, Object> params = ParamUtil.getEmptyMap();
        params.put("keyword", tableName);

        //获取资源信息
        List<SysTable> list = null;
        String cacheKey = Constants.CACHE_NAMESPACE + "SysTable" + ":" + tableName;
        String cacheValue = (String) CacheUtil.getCache().get(cacheKey);
        if (StringUtils.isNotBlank(cacheValue)) {
            list = JsonUtils.jsonToList(cacheValue, SysTable.class);
        } else {
            //获取资源信息
            list = sysTableMapper.selectTableByName(params);
            if (!list.isEmpty()) {
                CacheUtil.getCache().set(cacheKey, JsonUtils.objectToJson(list));
            }
        }

        return list;
    }
}

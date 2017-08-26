package com.loozb.service.sys.impl;

import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.loozb.core.Constants;
import com.loozb.core.util.CacheUtil;
import com.loozb.core.util.JsonUtils;
import com.loozb.core.util.ParamUtil;
import com.loozb.mapper.sys.CacheMapper;
import com.loozb.model.sys.Cache;
import com.loozb.model.RedisKeyValue;
import com.loozb.service.sys.CacheService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 龙召碧
 * @since 2017-07-10
 */
@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private CacheMapper cacheMapper;

    @Override
    public Object query(ModelMap modelMap, Integer current, Integer size, String keyword, String tableName) {
        String cacheKey = Constants.CACHE_NAMESPACE + replaceTable(tableName) + ":";
        PageHelper.startPage(current, size);
        Map<String, Object> params = ParamUtil.getMap();
        params.put("tableName", tableName);
        params.put("keyword", keyword);
        List<Cache> list = cacheMapper.selectData(params);
        List<Cache> result = new ArrayList<>();
        for (Cache c: list
             ) {
            c.setCacheKey(cacheKey + c.getId());
            Object o = CacheUtil.getCache().get(c.getCacheKey());
            if(o == null) {
                c.setIsCache("N");
            } else {
                c.setIsCache("Y");
            }
            result.add(c);
        }

        List<RedisKeyValue> rkvs = null;
        String rk = Constants.CACHE_NAMESPACE + "RedisKeyValue";
        String r = (String)CacheUtil.getCache().get(rk);
        if(StringUtils.isNotBlank(r)) {
            rkvs = JsonUtils.jsonToList(r, RedisKeyValue.class);
        } else {
            rkvs = cacheMapper.selectTableName();
            if (rkvs != null && rkvs.size() > 0) {
                CacheUtil.getCache().set(rk, JsonUtils.objectToJson(rkvs));
            }
        }
        modelMap.put("current", current);
        modelMap.put("size", size);
        modelMap.put("table", rkvs);
        modelMap.put("tableName", tableName);
        modelMap.put("total", PageHelper.getTotal());
        modelMap.put("iTotalRecords", PageHelper.getTotal());
        modelMap.put("iTotalDisplayRecords", PageHelper.getTotal());
        return list;
    }

    private String replaceTable(String tableName) {
        StringBuffer sb = new StringBuffer();
        sb.append(tableName.toLowerCase());
        int count = sb.indexOf("_");
        while (count != 0) {
            int num = sb.indexOf("_", count);
            count = num + 1;
            if (num != -1) {
                char ss = sb.charAt(count);
                char ia = (char) (ss - 32);
                sb.replace(count, count + 1, ia + "");
                System.out.println(sb.toString());
            }
        }
        String ss = sb.toString().replaceAll("_","");
        return ss.substring(0, 1).toUpperCase() + ss.substring(1);
    }

    @Override
    public void removeCurrentCache(String table) {
        String key = replaceTable(table);
        CacheUtil.getCache().delAll("LOOZB:" + key + "*");
    }
}
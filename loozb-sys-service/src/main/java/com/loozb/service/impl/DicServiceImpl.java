package com.loozb.service.impl;

import com.loozb.core.base.BaseServiceImpl;
import com.loozb.core.util.ParamUtil;
import com.loozb.model.Dic;
import com.loozb.service.DicService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *   服务实现类
 * </p>
 *
 * @author 龙召碧
 * @since 2017-07-10
 */
@Service
@CacheConfig(cacheNames = "Dic")
public class DicServiceImpl extends BaseServiceImpl<Dic> implements DicService {
    /**
     * 通过CODE获取配置信息
     *
     * @param code
     * @return
     */
    @Override
    public Object fetchDicsByCode(String code) {
        Map<String, Object> params = ParamUtil.getMap();

        Map<String, Object> result = ParamUtil.getMap();
        if(code.indexOf(",") == -1) {
            //此时说明没有查询多个，直接返回
            params.put("code", code);
            List<Dic> dics = super.queryList(params);
            result.put(code, dics);
        } else {
            String[] p = code.split(",");
            for (String c : p ) {
                params.clear();
                params.put("code", c);
                List<Dic> dics = super.queryList(params);
                result.put(c, dics);
            }
        }
        return result;
    }

    /**
     * 传入字符串
     *
     * @param deptId
     * @return
     */
    @Override
    public Dic queryById(String deptId) {
        if(StringUtils.isNotBlank(deptId)) {
            return super.queryById(Long.valueOf(deptId));
        }
        return null;
    }

    /**
     * 获取记录
     *
     * @param code
     * @param value
     * @return
     */
    @Override
    public Dic selectOne(String code, String value) {
        Map<String, Object> params = ParamUtil.getMap();
        params.put("code", code);
        params.put("value", value);
        List<Dic> dics = super.queryList(params);
        return (dics != null && dics.size() > 0) ? dics.get(0) : null;
    }

    /**
     * 根据name获取
     *
     * @param code
     * @param name
     * @return
     */
    @Override
    public Dic selectOneByName(String code, String name) {
        Map<String, Object> params = ParamUtil.getMap();
        params.put("name", name);
        params.put("code", code);
        List<Dic> dics = super.queryList(params);
        return (dics != null && dics.size() > 0) ? dics.get(0) : null;
    }
}
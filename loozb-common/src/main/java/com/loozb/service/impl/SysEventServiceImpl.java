package com.loozb.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.loozb.core.base.BaseServiceImpl;
import com.loozb.model.SysEvent;
import com.loozb.service.SysEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 操作日志表  服务实现类
 * </p>
 *
 * @author LongZhaoBi
 * @since 2017-05-20
 */
@Service
@CacheConfig(cacheNames = "SysEvent")
public class SysEventServiceImpl extends BaseServiceImpl<SysEvent> implements SysEventService{

    public Page<SysEvent> query(Map<String, Object> params) {
        Page<SysEvent> page = super.query(params);
        return page;
    }
}
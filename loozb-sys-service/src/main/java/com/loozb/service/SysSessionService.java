package com.loozb.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.loozb.core.Constants;
import com.loozb.core.base.BaseService;
import com.loozb.core.util.CacheUtil;
import com.loozb.core.util.InstanceUtil;
import com.loozb.core.util.PropertiesUtil;
import com.loozb.mapper.SysSessionMapper;
import com.loozb.model.SysSession;
import com.loozb.model.SysUser;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户角色信息表 服务类
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-26
 */
@Service
@CacheConfig(cacheNames = "SysSession")
public class SysSessionService extends BaseService<SysSession> {

    @Override
    public Page<SysSession> query(Map<String, Object> params) {
        Page<SysSession> page = super.query(params);
        List<SysSession> list = page.getRecords();
        List<SysSession> sessions = new ArrayList<SysSession>();
        //组装，查询是否在线
        for (SysSession session: list) {
            String token = session.getSessionId();
            SysUser user = (SysUser)CacheUtil.getCache().get(Constants.REDIS_SESSION_TOKEN + token);
            if(user != null) {
                //说明在线
                session.setOnline("1");
            } else {
                session.setOnline("0");
            }
            sessions.add(session);
        }
        page.setRecords(sessions);
        return page;
    }

    @CachePut
    @Transactional
    public SysSession update(SysSession record) {
        if (record.getId() == null) {
            record.setMtime(new Date());
            Long id = ((SysSessionMapper) mapper).queryBySessionId(record.getSessionId());
            if (id != null) {
                mapper.updateById(record);
            } else {
                record.setCtime(new Date());
                mapper.insert(record);
            }
        } else {
            mapper.updateById(record);
        }
        return record;
    }

    // 系统触发,由系统自动管理缓存
    public void deleteBySessionId(final SysSession sysSession) {
        ((SysSessionMapper) mapper).deleteBySessionId(sysSession.getSessionId());
    }

    public void deleteByUserId(final SysSession session) {
        ((SysSessionMapper) mapper).deleteByUserId(session.getUserId());
    }

    public List<String> querySessionIdByAccount(SysSession sysSession) {
        return ((SysSessionMapper) mapper).querySessionIdByAccount(sysSession.getAccount());
    }

    //
    public void cleanExpiredSessions() {
        String key = "spring:session:" + PropertiesUtil.getString("session.redis.namespace") + ":sessions:expires:";
        Map<String, Object> columnMap = InstanceUtil.newHashMap();
        List<SysSession> sessions = queryList(columnMap);
        for (SysSession sysSession : sessions) {
            logger.info("检查SESSION : {}", sysSession.getSessionId());
            if (!CacheUtil.getCache().exists(key + sysSession.getSessionId())) {
                logger.info("移除SESSION : {}", sysSession.getSessionId());
                delete(sysSession.getId());
            }
        }
    }
}

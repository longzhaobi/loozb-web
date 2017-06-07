package com.loozb.service;

import com.loozb.core.base.BaseService;
import com.loozb.model.SysUser;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-25
 */
public interface SysUserService extends BaseService<SysUser> {

    public SysUser queryById(Long id);

    public Long getUserIdByUsername(Map<String, Object> params);
}

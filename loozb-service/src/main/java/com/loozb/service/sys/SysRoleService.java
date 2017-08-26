package com.loozb.service.sys;

import com.loozb.core.base.BaseService;
import com.loozb.model.sys.SysRole;

import java.util.Map;

/**
 * <p>
 * 用户角色信息表 服务类
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-26
 */
public interface SysRoleService extends BaseService<SysRole> {

    void doAuth(Map<String, Object> params);
}

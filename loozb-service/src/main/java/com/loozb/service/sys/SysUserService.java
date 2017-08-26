package com.loozb.service.sys;

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

    /**
     * 判断用户名是否存在
     * @param value
     * @param code
     * @return
     */
    Object exist(String value, String code);

    /**
     * 修改密码
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     * @param id
     * @return
     */
    Object updatePassword(String oldPassword, String newPassword, String confirmPassword, Long id);

    /**
     * 验证权限
     * @param code
     * @param id
     * @return
     */
    Boolean verifyAuth(String code, Long id);

    /**
     * 初始化权限信息
     * @param id
     * @return
     */
    Object initAuth(Long id);
}

package com.loozb.service.impl;

import com.loozb.core.base.BaseServiceImpl;
import com.loozb.core.util.CacheUtil;
import com.loozb.core.util.JsonUtils;
import com.loozb.core.util.ParamUtil;
import com.loozb.core.util.PasswordUtil;
import com.loozb.model.SysAuth;
import com.loozb.model.SysResource;
import com.loozb.model.SysRole;
import com.loozb.model.SysUser;
import com.loozb.model.ext.Authority;
import com.loozb.service.SysResourceService;
import com.loozb.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-25
 */
@Service
@CacheConfig(cacheNames = "SysUser")
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService{

    @Autowired
    private SysAuthServiceImpl sysAuthService;

    @Autowired
    private SysRoleServiceImpl sysRoleService;

    @Autowired
    private SysResourceService sysResourceService;

    @Override
    @Transactional
    public SysUser queryById(Long id) {
        try {
            String key = getCacheKey(id);
            SysUser record = (SysUser) CacheUtil.getCache().get(key);
            if (record == null) {
                record = mapper.selectById(id);
                packageAuthInfo(record);
                CacheUtil.getCache().set(key, record);
            }
            return record;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void packageAuthInfo(SysUser user) {
        List<SysAuth> auths = sysAuthService.queryByUserId(user.getId());
        String roleIds = "";
        String roleNames = "";
        if (auths != null && auths.size() > 0) {
            for (int i = 0; i < auths.size(); i++) {
                SysAuth auth = auths.get(i);
                SysRole role = sysRoleService.queryById(auth.getRoleId());
                if (i == 0) {
                    roleIds += auth.getRoleId();
                    roleNames += role.getName();
                } else {
                    roleIds += "," + auth.getRoleId();
                    roleNames += "," + role.getName();
                }
            }
        }
        user.setRoleIds(roleIds);
        user.setRoleNames(roleNames);
    }

    public Long getUserIdByUsername(Map<String, Object> params) {
        List<SysUser> list = queryList(params);
        return list == null || list.size() == 0 ? null : list.get(0).getId();
    }

    /**
     * 判断用户名是否存在
     *
     * @param value
     * @param code
     * @return
     */
    @Override
    public Object exist(String value, String code) {
        Map<String, Object> params = ParamUtil.getMap();
        List<SysUser> list = null;
        if(code.equals("username")) {
            params.put("account", value);
            list = super.queryList(params);
        } else if(code.equals("idcard")) {
            params.put("idcard", value);
            list = super.queryList(params);
        }
        return (list != null && list.size() > 0) ? true : false;
    }

    /**
     * 修改密码
     *
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     * @param userId
     * @return
     */
    @Override
    public Object updatePassword(String oldPassword, String newPassword, String confirmPassword, Long userId) {
        if(StringUtils.isBlank(oldPassword)) {
            throw new IllegalArgumentException("获取原密码失败");
        }
        if(!newPassword.equals(confirmPassword)) {
            //去更新密码
            throw new IllegalArgumentException("两次输入的密码不一致");
        }
        SysUser user = super.queryById(userId);
        if (user == null) {
            throw new IllegalArgumentException("获取用户对象为空");
        }

        String password = PasswordUtil.decryptPassword(oldPassword, user.getSalt());

        if(password.equals(user.getPassword())) {
            //密码准确，进入修改密码
            //初始密码
            user.setPassword(newPassword);
            PasswordUtil.encryptPassword(user);

            //更新
            super.update(user);
        } else {
            throw new IllegalArgumentException("原密码不正确");
        }
        return user;
    }

    /**
     * 验证权限
     *
     * @param code
     * @param userId
     * @return
     */
    @Override
    public Boolean verifyAuth(String code, Long userId) {
        Set<String> sets = sysAuthService.findPermissions(userId);
        Boolean b = false;
        for (String set: sets
                ) {
            if(code.equals(set)) {
                b = true;
                break;
            }
        }
        return b;
    }

    /**
     * 初始化权限信息
     *
     * @param userId
     * @return
     */
    @Override
    public Object initAuth(Long userId) {
        SysUser user = queryById(userId);

        //获取角色信息
        Set<String> roles = sysAuthService.findRoles(userId);

        //获取权限信息
        Set<String> permissions = sysAuthService.findPermissions(userId);

        //获取资源信息
        List<SysResource> menus = null;
        String menuCacheKey = "REDIS:MENU:" + userId;
        String menuCache = (String) CacheUtil.getCache().get(menuCacheKey);
        if (StringUtils.isNotBlank(menuCache) && !menuCache.equals("[]")) {
            menus = JsonUtils.jsonToList(menuCache, SysResource.class);
        } else {
            //获取资源信息
            menus = sysResourceService.getMenus(userId);
            if (menus != null) {
                CacheUtil.getCache().set(menuCacheKey, JsonUtils.objectToJson(menus));
            }
        }
        return new Authority(roles, permissions, menus, user);
    }
}

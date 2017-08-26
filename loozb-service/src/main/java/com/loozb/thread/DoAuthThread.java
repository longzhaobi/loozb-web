package com.loozb.thread;

import com.loozb.core.util.CacheUtil;
import com.loozb.core.util.JsonUtils;
import com.loozb.model.sys.SysAuth;
import com.loozb.model.sys.SysResource;
import com.loozb.service.sys.SysAuthService;
import com.loozb.service.sys.SysResourceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created by chuan on 2017-7-3.
 */
public class DoAuthThread implements Runnable {

    private static Logger logger = LogManager.getLogger(DoAuthThread.class);

    private SysAuthService sysAuthService;
    private SysResourceService sysResourceService;
    private Long roleId;

    public DoAuthThread(SysAuthService sysAuthService, SysResourceService sysResourceService, Long roleId) {
        this.sysAuthService = sysAuthService;
        this.sysResourceService = sysResourceService;
        this.roleId = roleId;
    }

    @Override
    public void run() {
        logger.info("授权成功后，开始对角色进行重新授权");
        //查出所有拥有此角色的用户信息，并且循环重新授权
        List<SysAuth> authList = sysAuthService.queryByRoleId(Long.valueOf(roleId));
        for (SysAuth auth: authList
                ) {
            Long userId = auth.getUserId();
            String roleCacheKey = "REDIS:ROLE:" + userId;
            String permissionCacheKey = "REDIS:PERMISSION:" + userId;
            String menuCacheKey = "REDIS:MENU:" + userId;
            CacheUtil.getCache().del(roleCacheKey);
            CacheUtil.getCache().del(permissionCacheKey);
            CacheUtil.getCache().del(menuCacheKey);
            sysAuthService.findRoles(userId);
            sysAuthService.findPermissions(userId);
            List<SysResource> menus = sysResourceService.getMenus(userId);
            if (menus != null) {
                CacheUtil.getCache().set(menuCacheKey, JsonUtils.objectToJson(menus));
            }
        }
    }
}

package com.loozb.core.thread;

import com.loozb.service.sys.SysAuthService;

import java.util.Set;
import java.util.concurrent.Callable;

/**
 *  异步获取权限信息
 * Created by chuan on 2017-9-17.
 */
public class PermissionFutureData implements Callable<Set<String>> {

    private SysAuthService sysAuthService;
    private Long userId;

    public PermissionFutureData(SysAuthService sysAuthService, Long userId) {
        this.sysAuthService = sysAuthService;
        this.userId = userId;
    }
    @Override
    public Set<String> call() throws Exception {
        return sysAuthService.findPermissions(userId);
    }
}

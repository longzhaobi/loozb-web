package com.loozb.core.thread;

import com.loozb.model.ErrorInfo;
import com.loozb.model.SysUser;
import com.loozb.service.ErrorInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 保存错误信息
 * Created by chuan on 2017-7-3.
 */
public class DoSaveErrorInfoThread implements Runnable {

    private static Logger logger = LogManager.getLogger(DoSaveErrorInfoThread.class);

    private ErrorInfoService errorInfoService;
    private SysUser user;
    private Exception ex;
    private String httpCode;
    private String method;
    private String uri;
    private String agent;
    private String ip;
    private String uuid;

    public DoSaveErrorInfoThread(ErrorInfoService errorInfoService, SysUser user, Exception ex, String httpCode, String method, String uri, String agent, String ip, String uuid) {
        this.errorInfoService = errorInfoService;
        this.user = user;
        this.ex = ex;
        this.httpCode = httpCode;
        this.method = method;
        this.uri = uri;
        this.agent = agent;
        this.ip = ip;
        this.uuid = uuid;
    }

    @Override
    public void run() {
        logger.info("保存错误信息");
        try {
            ErrorInfo ei = new ErrorInfo();
            ei.setUrl(uri);
            ei.setCreateId(0L);
            ei.setName(user == null ? "游客":user.getUsername());
            ei.setAgent(agent);
            ei.setIp(ip);
            ei.setException(ex.getMessage() == null ? "未捕获到异常信息" : ex.getMessage().length() > 3000 ? ex.getMessage().substring(0, 3000) : ex.getMessage().replace("<br />", "，"));
            ei.setMethod(method);
            ei.setStatus(httpCode);
            ei.setUserId(user == null ? 0L : user.getId());
            ei.setUuid(uuid);
            ei.setInstance(ex.getClass().getName());

            errorInfoService.update(ei);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}

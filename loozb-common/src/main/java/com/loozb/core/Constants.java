package com.loozb.core;

import com.loozb.core.util.InstanceUtil;

import java.util.Map;

/**
 * 常量表
 * 
 * @author ShenHuaJie
 * @version $Id: Constants.java, v 0.1 2014-2-28 上午11:18:28 ShenHuaJie Exp $
 */
public interface Constants {
    /**
     * 异常信息统一头信息<br>
     * 非常遗憾的通知您,程序发生了异常
     */
    public static final String Exception_Head = "噢,我的天! 某些错误发生! 如下 :";
    /** 缓存键值 */
    public static final Map<Class<?>, String> cacheKeyMap = InstanceUtil.newHashMap();
    /** 操作名称 */
    public static final String OPERATION_NAME = "OPERATION_NAME";
    /** 客户端语言 */
    public static final String USERLANGUAGE = "userLanguage";
    /** 客户端主题 */
    public static final String WEBTHEME = "webTheme";
    /** 当前用户 */
    public static final String CURRENT_USER = "user";
    /** 当前TOKEN */
    public static final String CURRENT_TOKEN = "token";
    /** 上次请求地址 */
    public static final String PREREQUEST = "PREREQUEST";
    /** 上次请求时间 */
    public static final String PREREQUEST_TIME = "PREREQUEST_TIME";
    /** 登录地址 */
    public static final String LOGIN_URL = "/login.html";
    /** 非法请求次数 */
    public static final String MALICIOUS_REQUEST_TIMES = "MALICIOUS_REQUEST_TIMES";
    /** 缓存命名空间 */
    public static final String CACHE_NAMESPACE = "LOOZB:";
    /** 在线用户数量 */
    public static final String ALLUSER_NUMBER = "SYSTEM:" + CACHE_NAMESPACE + "ALLUSER_NUMBER";
    /** TOKEN */
    public static final String TOKEN_KEY = CACHE_NAMESPACE + "TOKEN_KEY";
    /** TOKEN */
    public static final String TOKEN = "token";
    /** permissioin_role**/
    public static final String SHIRO_REDIS_CACHE = CACHE_NAMESPACE + "SHIRO_REDIS_CACHE:";
    public static final String IMAGE_SERVER_BASE_URL = "http://images.loozb.com/";
    //保存用户登录记录
    public static final String REDIS_SESSION_ID = CACHE_NAMESPACE + "REDIS:SESSION:ID:";
    //保存用户登录记录
    public static final String REDIS_SESSION_TOKEN = CACHE_NAMESPACE + "REDIS:SESSION:TOKEN:";
    /** 日志表状态 */
    public interface JOBSTATE {
        /**
         * 日志表状态，初始状态，插入
         */
        public static final String INIT_STATS = "I";
        /**
         * 日志表状态，成功
         */
        public static final String SUCCESS_STATS = "S";
        /**
         * 日志表状态，失败
         */
        public static final String ERROR_STATS = "E";
        /**
         * 日志表状态，未执行
         */
        public static final String UN_STATS = "N";
    }
}

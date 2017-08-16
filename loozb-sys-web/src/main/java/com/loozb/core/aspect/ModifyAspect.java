package com.loozb.core.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotations.TableName;
import com.loozb.core.Constants;
import com.loozb.core.base.BaseModel;
import com.loozb.core.util.CacheUtil;
import com.loozb.core.util.DateUtil;
import com.loozb.core.util.ExceptionUtil;
import com.loozb.core.util.WebUtil;
import com.loozb.model.ModifyInfo;
import com.loozb.model.SysTable;
import com.loozb.model.SysUser;
import com.loozb.service.ModifyInfoService;
import com.loozb.service.SysTableService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 切面编程，识别所有注解@Modify的目标方法
 * <p>
 * 用于记录数据每个字段修改记录
 *
 * @Author： 龙召碧
 * @Date: Created in 2017-8-12 17:35
 */
@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ModifyAspect {

    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Autowired
    private SysTableService tableService;

    @Autowired
    private ModifyInfoService modifyInfoService;

    // 本地异常日志记录对象
    private static final Logger logger = Logger.getLogger(ModifyAspect.class);

    //记录key-value的值，此时的值为未修改的值
    private List<String> obs = new ArrayList<>();

    /**
     * 切入点
     * 凡是在方法上注解有ValidError，就会触发这里的业务逻辑
     */
    @Pointcut("@annotation(com.loozb.core.bind.annotation.Modify)")
    public void modifyAspect() {

    }

    /**
     * 环绕通知
     *
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("modifyAspect()")
    public Object modifyAspectAround(ProceedingJoinPoint point) throws Throwable {

        Object[] args = point.getArgs();

        Object obj = null;
        //识别只有继承BaseModel的实体
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof BaseModel) {
                if(args[i] != null) {
                    obj = args[i];
                    break;
                }
            }
        }

        //修改之前的值
        Object beforeObj = null;
        String cache = null;
        //获取ID
        Long id = null;
        if(obj != null) {

            //获取key值
            String cacheKey = obj.getClass().getSimpleName();
            Object idObj = null;
            try {
                Field field = obj.getClass().getSuperclass().getDeclaredField("id");//获取返回的值
                field.setAccessible(true);
                idObj = field.get(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (idObj == null) {
                //如果获取不到ID，直接返回
                return point.proceed();
            }
            //获取ID
            id = (Long) idObj;

            cache = Constants.CACHE_NAMESPACE + cacheKey + ":" + id;

            //调用目标方法前，从缓存获取当前的值
            beforeObj = CacheUtil.getCache().get(cache);

            if (beforeObj == null) {
                return point.proceed();
            }
        }
        //如果传参需要传Object[]类型，意思是去调目标方法，并用传的参数去改变目标方法上的参数，result为目标方法执行后返回的值
        //result为目标方法返回的值
        Object result = point.proceed();

        try {

            if (obj != null) {
                /**
                 * 获取实体上的注解
                 * TableName注解不是自定义注解，由mybatis-plus里提供
                 */
                TableName tn = obj.getClass().getAnnotation(TableName.class);
                String tableName = null;
                if (tn != null) {
                    tableName = tn.value();//获取表名
                }

                //只有获取到注解的表名才继续往下走
                if(StringUtils.isNotBlank(tableName)) {
                    //获取参数传入的对象值
                    JSONObject before = JSONObject.parseObject(JSON.toJSONString(beforeObj));

                    Object currentObj = CacheUtil.getCache().get(cache);//修改后的值

                    JSONObject current = JSONObject.parseObject(JSON.toJSONString(currentObj));

                    //获取表名
                    List<SysTable> list = tableService.selectTableByName(tableName);
                    Map<String, String> tableMap = new HashMap<>();
                    String tableInfo = null;
                    for (SysTable st: list
                            ) {
                        if(tableInfo == null) {
                            tableInfo = st.getTableComment();
                        }
                        tableMap.put(st.getColumnName(), st.getColumnComment());
                    }

                    StringBuffer content = new StringBuffer();//更改记录

                    Set<String> keys = current.keySet();

                    for (String key : keys) {
                        //循环比较，如果发现不同，将其写入buffer
                        String oldValue = before.getString(key) == null ? "" : before.getString(key);
                        String newValue = current.getString(key) == null ? "" : current.getString(key);
                        if(!oldValue.equals(newValue) && StringUtils.isNotBlank(oldValue) && !key.equals("mtime")) {
                            //假如不相等
                            content.append("【" + tableMap.get(key)+"】的值由【"+ oldValue +"】变为【"+newValue+"】<br/>");
                        }
                    }

                    if(StringUtils.isNotBlank(content)) {
                        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                                .getRequestAttributes()).getRequest();
                        //开辟现场保存错误信息
                        SysUser user = WebUtil.getCurrentUser(request);
                        String uri = request.getRequestURI();
                        String ip = WebUtil.getHost(request);

                        ModifyInfo mi = new ModifyInfo();
                        mi.setUrl(uri);
                        mi.setCreateId(0L);
                        mi.setIp(ip);
                        mi.setUserId(user.getId());
                        mi.setKey(id);
                        mi.setTableInfo(tableInfo);
                        mi.setTableName(tableName);
                        mi.setName(user.getUsername());
                        mi.setContent(content.toString());

                        executorService.submit(new Runnable() {
                            public void run() {
                                try { // 保存操作
                                    modifyInfoService.update(mi);
                                } catch (Exception e) {
                                    logger.error("Save modify log cause error :", e);
                                }
                            }
                        });
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //获取参数的值


        /**
         * 访问目标方法返回后
         */

        return result;
    }
}

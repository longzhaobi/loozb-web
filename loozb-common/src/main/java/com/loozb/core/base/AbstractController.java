/**
 * 
 */
package com.loozb.core.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import java.util.Date;
import java.util.Map;

/**
 * 控制器基类
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:47:58
 */
public abstract class AbstractController<T extends BaseService> extends BaseController {
    protected final Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    protected T service;

    public Object query(ModelMap modelMap, Map<String, Object> param) {
        return setSuccessModelMap(modelMap,  service.query(param));
    }

    public Object queryList(ModelMap modelMap, Map<String, Object> param) {
        return setSuccessModelMap(modelMap, service.queryList(param));
    }

    public Object queryById(ModelMap modelMap, Long id) {
        return setSuccessModelMap(modelMap, service.queryById(id));
    }

    public Object update(ModelMap modelMap, BaseModel param) {
        Long userId = getCurrUser();
        if (param.getId() == null && param.getCreateId() == null) {
            param.setCreateId(userId);
            param.setCtime(new Date());
        }
        Object o = service.update(param);
        return setSuccessModelMap(modelMap, o);
    }

    public Object delete(ModelMap modelMap, Long id) {
        service.delete(id);
        return setSuccessModelMap(modelMap);
    }

    public Object del(ModelMap modelMap, Long id) {
        service.del(id);
        return setSuccessModelMap(modelMap);
    }
}

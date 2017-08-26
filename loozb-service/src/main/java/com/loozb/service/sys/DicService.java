package com.loozb.service.sys;

import com.loozb.core.base.BaseService;
import com.loozb.model.sys.Dic;

/**
 * <p>
 *   服务实现类
 * </p>
 *
 * @author 龙召碧
 * @since 2017-07-10
 */
public interface DicService extends BaseService<Dic> {

    /**
     * 通过CODE获取配置信息
     * @param code
     * @return
     */
    Object fetchDicsByCode(String code);

    /**
     * 传入字符串
     * @param department
     * @return
     */
    Dic queryById(String department);

    /**
     * 获取记录
     * @param code
     * @param value
     * @return
     */
    Dic selectOne(String code, String value);

    /**
     *  根据name获取
     * @param code
     * @param name
     * @return
     */
    Dic selectOneByName(String code, String name);
}
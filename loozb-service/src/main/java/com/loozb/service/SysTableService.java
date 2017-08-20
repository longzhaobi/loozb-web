package com.loozb.service;

import com.loozb.model.SysTable;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-25
 */
public interface SysTableService {

    /**
     * 根据表明获取表字段和注释
     * @param tableName
     * @return
     */
    List<SysTable> selectTableByName(String tableName);

}

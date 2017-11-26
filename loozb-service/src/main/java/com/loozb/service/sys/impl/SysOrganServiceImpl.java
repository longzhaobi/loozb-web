package com.loozb.service.sys.impl;

import com.loozb.core.base.BaseServiceImpl;
import com.loozb.core.utils.TreeUtil;
import com.loozb.model.sys.SysOrgan;
import com.loozb.model.sys.SysResource;
import com.loozb.service.sys.SysOrganService;
import com.loozb.utils.OrganTreeUtil;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 组织机构部门信息表 服务类
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-26
 */
@Service
@CacheConfig(cacheNames = "SysOrgan")
public class SysOrganServiceImpl extends BaseServiceImpl<SysOrgan> implements SysOrganService{

    @Override
    public List<SysOrgan> queryList(Map<String, Object> params) {
        List<SysOrgan> list = super.queryList(params);

        /**
         * 通过资源的id和pid来进行父子组装
         */
        List<SysOrgan> treeList = null;
        try {
            treeList = OrganTreeUtil.buildListToTree(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return treeList;
    }
}

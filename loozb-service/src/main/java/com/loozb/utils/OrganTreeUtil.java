package com.loozb.utils;

import com.loozb.model.sys.SysOrgan;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 将资源转成对应的树关系
 * @Author： 龙召碧
 * @Date: Created in 2017-2-10 0:01
 */
public class OrganTreeUtil {
    @SuppressWarnings({ "unchecked" })
    public static List<SysOrgan> buildListToTree(List<SysOrgan> dirs) throws InvocationTargetException, IllegalAccessException {
        List<SysOrgan> roots = OrganTreeUtil.findRoots(dirs);
        List<SysOrgan> notRoots = (List<SysOrgan>) CollectionUtils
                .subtract(dirs, roots);
        for (SysOrgan root : roots) {
            root.setChildren(OrganTreeUtil.findChildren(root, notRoots));
        }
        return roots;
    }

    private static List<SysOrgan> findRoots(List<SysOrgan> allSysOrgans) throws InvocationTargetException, IllegalAccessException {
        List<SysOrgan> results = new ArrayList<SysOrgan>();
        for (SysOrgan resource : allSysOrgans) {
            boolean isRoot = true;
            for (SysOrgan comparedOne : allSysOrgans) {
                if (resource.getPid().longValue() == comparedOne.getId().longValue()) {
                    isRoot = false;
                    break;
                }
            }
            if (isRoot) {
                resource.setLeaf(false);
                results.add(resource);
            }
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    private static List<SysOrgan> findChildren(SysOrgan root,
                                                      List<SysOrgan> allSysOrgans) {
        List<SysOrgan> children = new ArrayList<SysOrgan>();

        for (SysOrgan comparedOne : allSysOrgans) {
            if (comparedOne.getPid().longValue() == root.getId().longValue()) {
                children.add(comparedOne);
            }
        }
        List<SysOrgan> notChildren = (List<SysOrgan>) CollectionUtils
                .subtract(allSysOrgans, children);
        for (SysOrgan child : children) {
            List<SysOrgan> tmpChildren = findChildren(child, notChildren);
            if (tmpChildren == null || tmpChildren.size() < 1) {
                // 叶子节点，如果needRoot为false，则移除
                child.setLeaf(true);

            } else {
                child.setLeaf(false);
            }
            child.setChildren(tmpChildren);

        }
        return children;
    }
}

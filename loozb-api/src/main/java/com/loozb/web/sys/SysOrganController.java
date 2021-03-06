package com.loozb.web.sys;

import com.loozb.core.base.AbstractController;
import com.loozb.core.bind.annotation.Modify;
import com.loozb.core.support.Assert;
import com.loozb.core.util.ParamUtil;
import com.loozb.model.sys.SysOrgan;
import com.loozb.service.sys.SysOrganService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 组织机构部门信息表 前端控制器
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-26
 */
@RestController
@Api(value = "机构管理", description = "机构管理")
@RequestMapping(value = "/organs")
public class SysOrganController extends AbstractController<SysOrganService> {
    @ApiOperation(value = "查询组织机构列表")
    @RequiresPermissions("organ:view")
    @GetMapping
    public Object query(ModelMap modelMap,
                        @ApiParam(required = false, value = "查询关键字") @RequestParam(value = "keyword", required = false) String keyword) {
        Map<String, Object> params = ParamUtil.getMap();
        params.put("keyword", keyword);
        params.put("available", "1");
        return super.queryList(modelMap, params);
    }

    /**
     * 创建创建组织机构
     * @param modelMap
     * @param param
     * @return
     */
    @PostMapping
    @ApiOperation(value = "创建组织机构信息")
    @RequiresPermissions("organ:create")
    public Object create(ModelMap modelMap, SysOrgan param) {
        return super.update(modelMap, param);
    }

    /**
     * 更新组织机构
     * @param modelMap
     * @param param
     * @return
     */
    @PutMapping
    @ApiOperation(value = "更新组织机构信息")
    @RequiresPermissions("organ:update")
    @Modify
    public Object update(ModelMap modelMap, SysOrgan param) {
        Assert.notNull(param, "organ");
        Assert.notNull(param.getId(), "ID");
        SysOrgan user = service.queryById(param.getId());
        Assert.notNull(user, "organ", param.getId());
        return super.update(modelMap, param);
    }

    /**
     * 删除组织机构
     * @param modelMap
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "更新组织机构信息")
    @RequiresPermissions("organ:remove")
    public Object remove(ModelMap modelMap, @PathVariable Long id) {
        return super.del(modelMap, id);
    }
}

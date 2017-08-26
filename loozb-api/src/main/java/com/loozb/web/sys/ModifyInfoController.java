package com.loozb.web.sys;

import com.loozb.core.base.AbstractController;
import com.loozb.core.util.ParamUtil;
import com.loozb.model.sys.ModifyInfo;
import com.loozb.service.sys.ModifyInfoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户角色信息表  前端控制器
 * </p>
 *
 * @author LongZhaoBi
 * @since 2017-08-13
 */
@RestController
@RequestMapping("/modifyInfos")
public class ModifyInfoController extends AbstractController<ModifyInfoService> {

    @ApiOperation(value = "查询用户角色信息表")
    @RequiresPermissions("modifyInfo:view")
    @GetMapping
    public Object query(ModelMap modelMap,
                        @ApiParam(required = false, value = "起始页") @RequestParam(defaultValue = "1", value = "current") String current,
                        @ApiParam(required = false, value = "查询页数") @RequestParam(defaultValue = "20", value = "size") String size,
                        @ApiParam(required = false, value = "需要排序字段") @RequestParam(defaultValue = "id", value = "orderBy") String orderBy,
                        @ApiParam(required = false, value = "查询关键字") @RequestParam(value = "keyword", required = false) String keyword) {
        return super.query(modelMap, ParamUtil.getPageParams(current, size, keyword, orderBy));
    }

    @ApiOperation(value = "用户角色信息表详情")
    @RequiresPermissions("modifyInfo:view")
    @GetMapping("/{id}")
    public Object get(ModelMap modelMap, @PathVariable Long id) {
        return super.queryById(modelMap, id);
    }

    @PostMapping
    @ApiOperation(value = "新增用户角色信息表")
    @RequiresPermissions("modifyInfo:create")
    public Object create(ModelMap modelMap, ModifyInfo param) {
        return super.update(modelMap, param);
    }

    @PutMapping
    @ApiOperation(value = "修改用户角色信息表")
    @RequiresPermissions("modifyInfo:update")
    public Object update(ModelMap modelMap, ModifyInfo param) {
        return super.update(modelMap, param);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户角色信息表")
    @RequiresPermissions("modifyInfo:remove")
    public Object delete(ModelMap modelMap, @PathVariable Long id) {
        return super.del(modelMap, id);

    }
}
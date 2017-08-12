package com.loozb.web;

import com.loozb.core.base.AbstractController;
import com.loozb.core.util.ParamUtil;
import com.loozb.model.ErrorInfo;
import com.loozb.service.ErrorInfoService;
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
@RequestMapping("/errorInfo")
public class ErrorInfoController extends AbstractController<ErrorInfoService> {

    @ApiOperation(value = "查询用户角色信息表")
    @RequiresPermissions("errorInfo:view")
    @GetMapping
    public Object query(ModelMap modelMap,
                        @ApiParam(required = false, value = "起始页") @RequestParam(defaultValue = "1", value = "current") String current,
                        @ApiParam(required = false, value = "查询页数") @RequestParam(defaultValue = "20", value = "size") String size,
                        @ApiParam(required = false, value = "需要排序字段") @RequestParam(defaultValue = "id", value = "orderBy") String orderBy,
                        @ApiParam(required = false, value = "查询关键字") @RequestParam(value = "keyword", required = false) String keyword) {
        return super.query(modelMap, ParamUtil.getPageParams(current, size, keyword, orderBy));
    }

    @ApiOperation(value = "用户角色信息表详情")
    @RequiresPermissions("errorInfo:view")
    @GetMapping("/{id}")
    public Object get(ModelMap modelMap, @PathVariable Long id) {
        return super.queryById(modelMap, id);
    }

    @PostMapping
    @ApiOperation(value = "新增用户角色信息表")
    @RequiresPermissions("errorInfo:create")
    public Object create(ModelMap modelMap, ErrorInfo param) {
        return super.update(modelMap, param);
    }

    @PutMapping
    @ApiOperation(value = "修改用户角色信息表")
    @RequiresPermissions("errorInfo:update")
    public Object update(ModelMap modelMap, ErrorInfo param) {
        return super.update(modelMap, param);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户角色信息表")
    @RequiresPermissions("errorInfo:remove")
    public Object delete(ModelMap modelMap, @PathVariable Long id) {
        return super.del(modelMap, id);

    }
}
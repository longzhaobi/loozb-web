package com.loozb.web.sys;

import com.loozb.core.base.AbstractController;
import com.loozb.core.util.ParamUtil;
import com.loozb.model.sys.Dic;
import com.loozb.service.sys.DicService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 龙召碧
 * @since 2017-07-10
 */
@RestController
@RequestMapping("/dics")
public class DicController extends AbstractController<DicService> {

    @ApiOperation(value = "查询")
    @RequiresPermissions("dic:view")
    @GetMapping
    public Object query(ModelMap modelMap,
                        @ApiParam(required = false, value = "起始页") @RequestParam(defaultValue = "1", value = "current") String current,
                        @ApiParam(required = false, value = "查询页数") @RequestParam(defaultValue = "20", value = "size") String size,
                        @ApiParam(required = false, value = "需要排序字段") @RequestParam(defaultValue = "id", value = "orderBy") String orderBy,
                        @ApiParam(required = false, value = "查询关键字") @RequestParam(value = "keyword", required = false) String keyword) {
        return super.query(modelMap, ParamUtil.getPageParams(current, size, keyword, orderBy));
    }

    @ApiOperation(value = "详情")
    @RequiresPermissions("dic:view")
    @GetMapping("/{id}")
    public Object get(ModelMap modelMap, @PathVariable Long id) {
        return super.queryById(modelMap, id);
    }

    @PostMapping
    @ApiOperation(value = "新增")
    @RequiresPermissions("dic:create")
    public Object create(ModelMap modelMap, Dic param) {
        return super.update(modelMap, param);
    }

    @PutMapping
    @ApiOperation(value = "修改")
    @RequiresPermissions("dic:update")
    public Object update(ModelMap modelMap, Dic param) {
        return super.update(modelMap, param);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除")
    @RequiresPermissions("dic:remove")
    public Object delete(ModelMap modelMap, @PathVariable Long id) {
        return super.del(modelMap, id);

    }

    @GetMapping("/fetchDics")
    @ApiOperation(value = "获取数据字典配置信息")
    public Object fetchDics(ModelMap modelMap, String code) {
        return setSuccessModelMap(modelMap, service.fetchDicsByCode(code));
    }
}
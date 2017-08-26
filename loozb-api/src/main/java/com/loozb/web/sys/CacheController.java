package com.loozb.web.sys;

import com.loozb.core.base.BaseController;
import com.loozb.core.util.CacheUtil;
import com.loozb.service.sys.CacheService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 缓存管理控制器
 * </p>
 *
 * @author 龙召碧
 * @since 2017-07-10
 */
@RestController
@RequestMapping("/caches")
public class CacheController extends BaseController {

    @Autowired
    private CacheService cacheService;

    @ApiOperation(value = "查询")
    @RequiresPermissions("cache:view")
    @GetMapping
    public Object query(ModelMap modelMap,
                        @ApiParam(required = false, value = "起始页") @RequestParam(defaultValue = "1", value = "current") Integer current,
                        @ApiParam(required = false, value = "查询页数") @RequestParam(defaultValue = "20", value = "size") Integer size,
                        @ApiParam(required = false, value = "查询关键字") @RequestParam(value = "keyword", required = false) String keyword, String tableName) {
        return setSuccessModelMap(modelMap, cacheService.query(modelMap, current, size, keyword, tableName));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除")
    @RequiresPermissions("cache:remove")
    public Object delete(ModelMap modelMap, @PathVariable String id) {
        CacheUtil.getCache().del(id);
        return setSuccessModelMap(modelMap);

    }

    @PostMapping("/removeCache")
    @ApiOperation(value = "清除所有缓存")
    @RequiresPermissions("cache:remove")
    public Object removeCache(ModelMap modelMap, String cacheKey) {
        if(StringUtils.isBlank(cacheKey)) {
            throw new IllegalArgumentException("传出参数key为空");
        }
        CacheUtil.getCache().delAll(cacheKey + "*");
        return setSuccessModelMap(modelMap);

    }

    @PostMapping("/removeCurrentCache")
    @ApiOperation(value = "清除当前缓存")
    @RequiresPermissions("cache:remove")
    public Object removeCurrentCache(ModelMap modelMap, String table) {
        if(StringUtils.isBlank(table)) {
            throw new IllegalArgumentException("传出参数为空");
        }
        cacheService.removeCurrentCache(table);
        return setSuccessModelMap(modelMap);

    }
}
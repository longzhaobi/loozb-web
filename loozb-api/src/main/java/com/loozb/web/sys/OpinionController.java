package com.loozb.web.sys;
import com.loozb.core.base.AbstractController;
import com.loozb.core.bind.annotation.CurrentUser;
import com.loozb.core.util.ParamUtil;
import com.loozb.model.SysUser;
import com.loozb.model.sys.Opinion;
import com.loozb.service.sys.OpinionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;


/**
 * <p>
 * 意见反馈  前端控制器
 * </p>
 *
 * @author LongZhaoBi
 * @since 2017-11-19
 */
@RestController
@RequestMapping("/opinions")
@Api(value = "意见反馈", description = "意见反馈接口信息")
public class OpinionController extends AbstractController<OpinionService> {

    @ApiOperation(value = "查询意见反馈信息")
    @RequiresPermissions("opinion:view")
    @GetMapping
    public Object query(ModelMap modelMap,
                        @ApiParam(required = false, value = "起始页") @RequestParam(defaultValue = "1", value = "current") String current,
                        @ApiParam(required = false, value = "查询页数") @RequestParam(defaultValue = "20", value = "size") String size,
                        @ApiParam(required = false, value = "需要排序字段") @RequestParam(defaultValue = "id", value = "orderBy") String orderBy,
                        @ApiParam(required = false, value = "查询关键字") @RequestParam(value = "keyword", required = false) String keyword) {
        return super.query(modelMap, ParamUtil.getPageParams(current, size, keyword, orderBy));
    }

    @ApiOperation(value = "意见反馈详情")
    @RequiresPermissions("opinion:view")
    @GetMapping("/{id}")
    public Object get(ModelMap modelMap, @PathVariable Long id) {
        return super.queryById(modelMap, id);
    }

    @PostMapping
    @ApiOperation(value = "新增意见反馈")
    @RequiresPermissions("opinion:create")
    public Object create(ModelMap modelMap, Opinion param) {
        return super.update(modelMap, param);
    }

    @PutMapping
    @ApiOperation(value = "修改意见反馈")
    @RequiresPermissions("opinion:update")
    public Object update(ModelMap modelMap, Opinion param, @CurrentUser SysUser user) {
        if(null != user) {
            param.setReplyUserId(user.getId());
        }
        param.setReplyStatus("1");
        return super.update(modelMap, param);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除意见反馈")
    @RequiresPermissions("opinion:remove")
    public Object delete(ModelMap modelMap, @PathVariable Long id) {
        return super.del(modelMap, id);

    }
}
package com.loozb.web.sys;

import com.loozb.core.base.AbstractController;
import com.loozb.core.bind.annotation.CurrentUser;
import com.loozb.core.bind.annotation.Modify;
import com.loozb.core.support.Assert;
import com.loozb.core.support.HttpCode;
import com.loozb.core.util.ParamUtil;
import com.loozb.core.util.PasswordUtil;
import com.loozb.core.util.WebUtil;
import com.loozb.model.SysUser;
import com.loozb.service.sys.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-25
 */
@RestController
@Api(value = "用户管理", description = "用户管理")
@RequestMapping(value = "/users")
public class SysUserController extends AbstractController<SysUserService> {

    // 查询用户列表
    @ApiOperation(value = "查询用户列表，默认查询20条")
    @RequiresPermissions("user:view")
    @GetMapping
    public Object query(ModelMap modelMap,
                        @ApiParam(required = false, value = "起始页") @RequestParam(defaultValue = "1", value = "current") String current,
                        @ApiParam(required = false, value = "查询页数") @RequestParam(defaultValue = "20", value = "size") String size,
                        @ApiParam(required = false, value = "需要排序字段") @RequestParam(defaultValue = "id", value = "orderBy") String orderBy,
                        @ApiParam(required = false, value = "查询关键字") @RequestParam(value = "keyword", required = false) String keyword) {
        return super.query(modelMap,  ParamUtil.getPageParams(current, size, keyword, orderBy));
    }

    /**
     * 创建创建用户
     * @param modelMap
     * @param param
     * @return
     */
    @PostMapping
    @ApiOperation(value = "创建用户信息")
    @RequiresPermissions("user:create")
    public Object create(ModelMap modelMap, SysUser param) {
//        if(StringUtils.isNotBlank(param.getIdcard())) {
//            Assert.idCard(param.getIdcard());
//        }
        PasswordUtil.encryptPassword(param);
        return super.update(modelMap, param);
    }

    /**
     * 更新用户
     * @param modelMap
     * @param param
     * @return
     */
    @PutMapping
    @ApiOperation(value = "更新用户信息")
    @RequiresPermissions("user:update")
    @Modify
    public Object update(ModelMap modelMap, SysUser param) {
        Assert.notNull(param, "USER");
        Assert.notNull(param.getId(), "ID");
//        if(StringUtils.isNotBlank(param.getIdcard())) {
//            Assert.idCard(param.getIdcard());
//        }
        SysUser user = service.queryById(param.getId());
        Assert.notNull(user, "USER", param.getId());
        if(StringUtils.isNotBlank(param.getPassword())) {
            if (!param.getPassword().equals(user.getPassword())) {
                PasswordUtil.encryptPassword(param);
            }
        }
        return super.update(modelMap, param);
    }

    /**
     * 删除用户
     * @param modelMap
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "更新用户信息")
    @RequiresPermissions("user:remove")
    public Object remove(ModelMap modelMap, @PathVariable Long id) {
        return super.del(modelMap, id);
    }

    /**
     * 获取当前在线用户
     * @param modelMap
     * @return
     */
    @GetMapping("/current")
    @ApiOperation(value = "获取当前用户信息")
    public Object current(ModelMap modelMap, @CurrentUser SysUser user) {
        //获取在线人数
        Integer number = WebUtil.getAllUserNumber();
        modelMap.put("online", number);
        return setSuccessModelMap(modelMap, service.initAuth(user.getId()));
    }

    /**
     * 验证权限信息
     * @param modelMap
     * @return
     */
    @GetMapping("/verifyAuth")
    @ApiOperation(value = "验证当前用户权限")
    public Object verifyAuth(ModelMap modelMap, String code, @CurrentUser SysUser user) {
        Boolean bool = service.verifyAuth(code, user.getId());
        if(bool) {
            return setModelMap(modelMap, HttpCode.OK);
        } else {
            return setModelMap(modelMap, HttpCode.FORBIDDEN);
        }
    }

    /**
     * 修改密码
     * @param modelMap
     * @return
     */
    @PutMapping("/updatePassword")
    @ApiOperation(value = "修改密码")
    public Object updatePassword(ModelMap modelMap, String oldPassword, String newPassword, String confirmPassword, @CurrentUser SysUser user) {
        return setSuccessModelMap(modelMap, service.updatePassword(oldPassword, newPassword, confirmPassword, user.getId()));
    }

    /**
     * 判断用户名是否存在
     * @param modelMap
     * @return
     */
    @GetMapping("/exist")
    @ApiOperation(value = "判断用户名是否存在")
    public Object exist(ModelMap modelMap, String value, String code) {
        return setSuccessModelMap(modelMap, service.exist(value, code));
    }

    // 查询用户列表
    @ApiOperation(value = "查询在线用户")
    @RequiresPermissions("user:view")
    @GetMapping("/onlineUser")
    public Object queryOnlineUser(ModelMap modelMap, @RequestParam(value = "keyword", required = false) String keyword) {
        return setSuccessModelMap(modelMap, service.queryOnlineUser(modelMap, keyword));
    }

}

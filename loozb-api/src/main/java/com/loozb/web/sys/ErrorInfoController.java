package com.loozb.web.sys;

import com.loozb.core.base.AbstractController;
import com.loozb.core.bind.annotation.CurrentUser;
import com.loozb.core.util.ParamUtil;
import com.loozb.core.util.WebUtil;
import com.loozb.model.ErrorInfo;
import com.loozb.model.SysUser;
import com.loozb.service.ErrorInfoService;
import cz.mallat.uasparser.OnlineUpdater;
import cz.mallat.uasparser.UASparser;
import cz.mallat.uasparser.UserAgentInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

/**
 * <p>
 * 用户角色信息表  前端控制器
 * </p>
 *
 * @author LongZhaoBi
 * @since 2017-08-13
 */
@RestController
@RequestMapping("/errorInfos")
public class ErrorInfoController extends AbstractController<ErrorInfoService> {

    @ApiOperation(value = "查询用户角色信息表")
    @GetMapping
    public Object query(ModelMap modelMap,
                        @ApiParam(required = false, value = "起始页") @RequestParam(defaultValue = "1", value = "current") String current,
                        @ApiParam(required = false, value = "查询页数") @RequestParam(defaultValue = "20", value = "size") String size,
                        @ApiParam(required = false, value = "需要排序字段") @RequestParam(defaultValue = "id", value = "orderBy") String orderBy,
                        @ApiParam(required = false, value = "查询关键字") @RequestParam(value = "keyword", required = false) String keyword) {
        return super.query(modelMap, ParamUtil.getPageParams(current, size, keyword, orderBy));
    }

    @ApiOperation(value = "用户角色信息表详情")
    @GetMapping("/{id}")
    public Object get(ModelMap modelMap, @PathVariable Long id) {
        return super.queryById(modelMap, id);
    }

    @PostMapping
    @ApiOperation(value = "新增用户角色信息表")
    public Object create(ModelMap modelMap, ErrorInfo param, HttpServletRequest request, @CurrentUser SysUser user) {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String ip = WebUtil.getHost(request);
        String uuid = UUID.randomUUID().toString().replace("-", "");
        param.setIp(ip);
        param.setMethod(method);
        param.setName(user == null ? "游客" : user.getUsername());
        param.setUserId(user == null ? 0L : user.getId());
        param.setStatus("300");
        param.setUrl(uri);
        param.setUuid(uuid);
        param.setCreateId(user == null ? 0L : user.getId());

        try {
            UASparser uasParser = new UASparser(OnlineUpdater.getVendoredInputStream());
            UserAgentInfo userAgentInfo = uasParser.parse(request.getHeader("user-agent"));
            String userAgent = userAgentInfo.getOsName() + " " + userAgentInfo.getType() + " " + userAgentInfo.getUaName();
            param.setAgent(userAgent);
        } catch (IOException e) {
            logger.error("", e);
        }
        return super.update(modelMap, param);
    }

    @PutMapping
    @ApiOperation(value = "修改用户角色信息表")
    public Object update(ModelMap modelMap, ErrorInfo param) {
        return super.update(modelMap, param);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户角色信息表")
    public Object delete(ModelMap modelMap, @PathVariable Long id) {
        return super.del(modelMap, id);

    }
}
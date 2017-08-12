/**
 * 
 */
package com.loozb.core.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.plugins.Page;
import com.loozb.core.Constants;
import com.loozb.core.exception.BaseException;
import com.loozb.core.exception.IllegalParameterException;
import com.loozb.core.support.HttpCode;
import com.loozb.core.thread.DoSaveErrorInfoThread;
import com.loozb.core.util.InstanceUtil;
import com.loozb.core.util.WebUtil;
import com.loozb.model.SysUser;
import com.loozb.service.ErrorInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 控制器基类
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:47:58
 */
public abstract class BaseController {
	protected final Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	ErrorInfoService errorInfoService;

	/** 获取当前用户Id */
	protected Long getCurrUser() {
		return WebUtil.getCurrentUser();
	}

	/** 设置成功响应代码 */
	protected ResponseEntity<ModelMap> setSuccessModelMap(ModelMap modelMap) {
		return setSuccessModelMap(modelMap, null);
	}

	/** 设置成功响应代码 */
	protected ResponseEntity<ModelMap> setSuccessModelMap(ModelMap modelMap, Object data) {
		return setModelMap(modelMap, HttpCode.OK, data);
	}

	/** 设置响应代码 */
	protected ResponseEntity<ModelMap> setModelMap(ModelMap modelMap, HttpCode code) {
		return setModelMap(modelMap, code, null);
	}

	/** 设置响应代码 */
	protected ResponseEntity<ModelMap> setModelMap(ModelMap modelMap, HttpCode code, Object data) {
		Map<String, Object> map = InstanceUtil.newLinkedHashMap();
		map.putAll(modelMap);
		modelMap.clear();
		for (Iterator<String> iterator = map.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
			if (!key.startsWith("org.springframework.validation.BindingResult") && !key.equals("void")) {
				modelMap.put(key, map.get(key));
			}
		}
		if (data != null) {
			if (data instanceof Page) {
				Page<?> page = (Page<?>) data;
				modelMap.put("data", page.getRecords());
				modelMap.put("current", page.getCurrent());
				modelMap.put("size", page.getSize());
				modelMap.put("pages", page.getPages());
				modelMap.put("total", page.getTotal());
				modelMap.put("iTotalRecords", page.getTotal());
				modelMap.put("iTotalDisplayRecords", page.getTotal());
			} else if (data instanceof List<?>) {
				modelMap.put("data", data);
				modelMap.put("iTotalRecords", ((List<?>) data).size());
				modelMap.put("iTotalDisplayRecords", ((List<?>) data).size());
			} else {
				modelMap.put("data", data);
			}
		}
		modelMap.put("httpCode", code.value());
		modelMap.put("msg", code.msg());
		modelMap.put("timestamp", System.currentTimeMillis());
		return ResponseEntity.ok(modelMap);
	}

	/** 异常处理，所有的异常都会调用这里,并写入日志 */
	@ExceptionHandler(Exception.class)
	public void exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex)
			throws Exception {
		logger.error(Constants.Exception_Head, ex);
		Integer httpCode = 400;
		ModelMap modelMap = new ModelMap();
		if (ex instanceof BaseException) {
			((BaseException) ex).handler(modelMap);
		} else if (ex instanceof IllegalArgumentException) {
			new IllegalParameterException(ex.getMessage()).handler(modelMap);
		} else if (ex instanceof UnauthorizedException) {
			httpCode = HttpCode.FORBIDDEN.value();
			modelMap.put("httpCode", httpCode);
			modelMap.put("msg", StringUtils.defaultIfBlank(ex.getMessage(), HttpCode.FORBIDDEN.msg()));
		} else {
			httpCode = HttpCode.INTERNAL_SERVER_ERROR.value();
			modelMap.put("httpCode", httpCode);
			String msg = StringUtils.defaultIfBlank(ex.getMessage(), HttpCode.INTERNAL_SERVER_ERROR.msg());
			modelMap.put("msg", msg.length() > 100 ? "系统走神了,请稍候再试." : msg);
		}
		response.setContentType("application/json;charset=UTF-8");
		modelMap.put("timestamp", System.currentTimeMillis());
		logger.info(JSON.toJSON(modelMap));
		//开辟现场保存错误信息
		SysUser user = WebUtil.getCurrentUser(request);
		String method = request.getMethod();
		String uri = request.getRequestURI();
		String ip = WebUtil.getHost(request);
		String agent = request.getHeader("user-agent");
		String uuid = UUID.randomUUID().toString().replace("-", "");
		modelMap.put("uuid", uuid);
		new Thread(new DoSaveErrorInfoThread(errorInfoService, user, ex, httpCode.toString(), method, uri, agent, ip, uuid)).start();
		byte[] bytes = JSON.toJSONBytes(modelMap, SerializerFeature.DisableCircularReferenceDetect);
		response.getOutputStream().write(bytes);
	}
}

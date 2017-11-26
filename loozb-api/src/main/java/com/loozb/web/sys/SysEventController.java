package com.loozb.web.sys;

import com.loozb.core.base.AbstractController;
import com.loozb.core.util.FileUtil;
import com.loozb.core.util.ParamUtil;
import com.loozb.core.util.PropertiesUtil;
import com.loozb.model.sys.ext.FileInfo;
import com.loozb.service.SysEventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 龙召碧
 * @since 2017-02-26
 */
@RestController
@Api(value = "日志管理", description = "日志管理")
@RequestMapping(value = "/events")
public class SysEventController extends AbstractController<SysEventService> {

    // 查询权限列表
    @ApiOperation(value = "获取日志列表")
    @RequiresPermissions("event:view")
    @GetMapping
    public Object query(ModelMap modelMap,
                        @ApiParam(required = false, value = "起始页") @RequestParam(defaultValue = "1", value = "current") String current,
                        @ApiParam(required = false, value = "查询页数") @RequestParam(defaultValue = "20", value = "size") String size,
                        @ApiParam(required = false, value = "需要排序字段") @RequestParam(defaultValue = "id", value = "orderBy") String orderBy,
                        @ApiParam(required = false, value = "查询关键字") @RequestParam(value = "keyword", required = false) String keyword) {
        return super.query(modelMap,  ParamUtil.getPageParams(current, size, keyword, orderBy));
    }

    // 获取文件日志
    @ApiOperation(value = "获取系统日志")
    @RequiresPermissions("event:view")
    @GetMapping("/downFile")
    public void downFile(HttpServletResponse response, String filePath, String fileName) throws IOException {
        logger.info("----开始下载日志文件----");
        response.setContentType("text/plain");
        response.setHeader("Location",fileName);
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        OutputStream outputStream = response.getOutputStream();
        InputStream inputStream = new FileInputStream(filePath);
        byte[] buffer = new byte[1024];
        int i = -1;
        while ((i = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, i);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    // 获取文件日志
    @ApiOperation(value = "获取系统日志")
    @RequiresPermissions("event:view")
    @GetMapping("/fileList")
    public Object queryFileList(ModelMap modelMap) {
        String logPath = PropertiesUtil.getString("file.logs.path");
        if(StringUtils.isBlank(logPath)) {
            throw new RuntimeException("获取查询文件路径失败");
        }
        List<FileInfo> fileInfoList = generateFileList(logPath);
        return setSuccessModelMap(modelMap, fileInfoList);
    }

    private List<FileInfo> generateFileList(String filePath) {
        List<FileInfo> children = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        //读取文件
        List<File> fileList = FileUtil.getFileList(filePath);
        for (File file : fileList) {
            FileInfo f = new FileInfo();
            f.setFileName(file.getName());
            f.setFilePath(file.getAbsolutePath());
            cal.setTimeInMillis(file.lastModified());
            f.setFileTime(sdf.format(cal.getTime()));
            int size = (int)(file.length() /1024) + 1;
            f.setFileSize(size);
            if(file.isFile()) {
                //如果是文件
                f.setIsDirectory("N");
            } else {
                //是目录
//                f.setFileSize(0);
                f.setIsDirectory("Y");
            }
            children.add(f);

            //假如还有目录，在递归
            if("Y".equals(f.getIsDirectory())) {
                f.setChildren(generateFileList(f.getFilePath()));
            }
        }
        return children;
    }

}

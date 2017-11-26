//package com.loozb.core.utils;
//
//import com.alibaba.fastjson.JSON;
//import com.loozb.core.util.FileUtil;
//import com.loozb.core.util.PropertiesUtil;
//import com.loozb.model.sys.ext.FileInfo;
//import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
//import org.apache.commons.lang3.StringUtils;
//
//import java.io.File;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//
///**
// * @author 龙召碧
// * @create 2017-11-20 14:17
// **/
//public class TestUtils {
//
//    public static void main(String[] args) {
////        String logPath = PropertiesUtil.getString("file.logs.path");
//        String logPath = "/output/logs";
//        List<FileInfo> fileInfoList = generateFileList(logPath);
//        System.out.println(fileInfoList);
//        String jsonStr = JSON.toJSONString(fileInfoList);
//        System.out.println(jsonStr);
//    }
//
//    public static List<FileInfo> generateFileList(String filePath) {
//        List<FileInfo> children = new ArrayList<>();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Calendar cal = Calendar.getInstance();
//        //读取文件
//        List<File> fileList = FileUtil.getFileList(filePath);
//        for (File file : fileList) {
//            FileInfo f = new FileInfo();
//            f.setFileName(file.getName());
//            f.setFilePath(file.getAbsolutePath());
//            cal.setTimeInMillis(file.lastModified());
//            f.setFileTime(sdf.format(cal.getTime()));
//            //如果是文件
//            int size = (int)(file.length() /1024) + 1;
//            f.setFileSize(size);
//            if(file.isFile()) {
//
//                f.setIsDirectory("N");
//            } else {
//                //是目录
////                f.setFileSize(0);
//                f.setIsDirectory("Y");
//                //递归组装孩子
////                fi.setChildren(generateFileList(fi, fi.getFilePath()));
//            }
//            children.add(f);
//
//            //假如还有目录，在递归
//            if("Y".equals(f.getIsDirectory())) {
//                f.setChildren(generateFileList(f.getFilePath()));
//            }
////            fi.setChildren(children);
//        }
//        return children;
//    }
//}

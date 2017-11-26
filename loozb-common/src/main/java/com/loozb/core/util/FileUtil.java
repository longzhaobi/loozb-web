package com.loozb.core.util;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 文件处理
 *
 * @author 龙召碧
 * @create 2017-11-20 11:09
 **/
public class FileUtil {
    public static List<File> getFileList(String strPath) {
        List<File> filelist = new ArrayList<>();
        File dir = new File(strPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
//                String fileName = files[i].getName();
//                if (files[i].isDirectory()) {
//                    getFileList(files[i].getAbsolutePath());
//                } else if (fileName.endsWith("log")) {
//                    filelist.add(files[i]);
//                } else {
//                    continue;
//                }
                filelist.add(files[i]);
            }
        }
        return filelist;
    }

    public static void main(String[] args) {
        List<File> list = FileUtil.getFileList("/output/logs/error");
        System.out.println(list.size());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        for (File f: list) {
            System.out.println("文件名:" + f.getName());
            System.out.println("文件路径:" + f.getAbsolutePath());
            System.out.println("目录：" + f.getPath());
            cal.setTimeInMillis(f.lastModified());
            System.out.println("文件时间:" + sdf.format(cal.getTime()));
            int size = (int)(f.length() /1024) + 1;
            System.out.println("文件大小:" + size);
        }
    }
}

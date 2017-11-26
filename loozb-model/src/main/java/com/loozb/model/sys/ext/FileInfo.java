package com.loozb.model.sys.ext;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件
 *
 * @author 龙召碧
 * @create 2017-11-20 11:48
 **/
public class FileInfo {
    //文件名称
    private String fileName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 是否是目录
     */
    private String isDirectory;

    /**
     * 文件时间
     */
    private String fileTime;

    /**
     * 文件大小
     */
    private int fileSize;

    private List<FileInfo> children = new ArrayList<>();

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getIsDirectory() {
        return isDirectory;
    }

    public void setIsDirectory(String isDirectory) {
        this.isDirectory = isDirectory;
    }

    public String getFileTime() {
        return fileTime;
    }

    public void setFileTime(String fileTime) {
        this.fileTime = fileTime;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public List<FileInfo> getChildren() {
        return children;
    }

    public void setChildren(List<FileInfo> children) {
        this.children = children;
    }

}

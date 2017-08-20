package com.loozb.model;

import java.io.Serializable;

/**
 * 表名和表注释
 * @Author： 龙召碧
 * @Date: Created in 2017-3-12 13:51
 */
public class SysTable implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String ordialPosition;// 字段排序
    private String columnName;// 字段名
    private String tableName;// 表名
    private String columnDefault;// 默认值
    private String isNullable;// 是否为空，NO / YES
    private String columnType;// 字段类型
    private String columnKey;// 主键
    private Integer nums;//数字
    private String extra;// 额外备注
    private String columnComment;// 备注
    private String tableComment;//表注释

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrdialPosition() {
        return ordialPosition;
    }

    public void setOrdialPosition(String ordialPosition) {
        this.ordialPosition = ordialPosition;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnDefault() {
        return columnDefault;
    }

    public void setColumnDefault(String columnDefault) {
        this.columnDefault = columnDefault;
    }

    public String getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public Integer getNums() {
        return nums;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }
}

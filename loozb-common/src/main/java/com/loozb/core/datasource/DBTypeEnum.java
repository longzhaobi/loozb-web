package com.loozb.core.datasource;

/**
 * 龙召碧
 * Created by chuan on 2017-7-19.
 */
public enum  DBTypeEnum {
    one("dataSource_one"), two("dataSource_two");
    private String value;

    DBTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

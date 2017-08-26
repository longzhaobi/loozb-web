package com.loozb.model.sys;

import com.loozb.core.base.BaseModel;

import java.util.Date;

/**
 * Created by chuan on 2017-8-26.
 */
public class Cache{
    private String id;
    private String cacheKey;
    private String isCache;
    private Date ctime;
    private Date mtime;

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public String getIsCache() {
        return isCache;
    }

    public void setIsCache(String isCache) {
        this.isCache = isCache;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }
}

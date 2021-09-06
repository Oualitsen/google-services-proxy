/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model.cache;

import com.pinitservices.proxy.model.BasicEntity;

/**
 *
 * @author Ramdane
 */

public class CacheHit extends BasicEntity {

    private String cacheId;
    /**
     * Hit means that returned from cache Non hit means returned from API
     */
    private boolean hit;

    private String userId;
    private String cachetype;
    private String type;

    public CacheHit() {
    }

    public CacheHit(String cacheId, boolean isHit, String userId, String cacheType) {
        this.cacheId = cacheId;
        this.hit = isHit;
        this.userId = userId;
        this.cachetype = cacheType;
        type = this.hit ? "cache" : "API";
    }

    public String getCacheId() {
        return cacheId;
    }

    public void setCacheId(String cacheId) {
        this.cacheId = cacheId;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCachetype() {
        return cachetype;
    }

    public void setCachetype(String cachetype) {
        this.cachetype = cachetype;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}

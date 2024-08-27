/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model;



import com.pinitservices.proxy.BasicEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Ramdane
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CacheHit extends BasicEntity {

    private String cacheId;
    /**
     * Hit means that returned from cache Non hit means returned from API
     */
    private boolean isHit;

    private String userId;
    private String cacheType;
    private String type;

    public CacheHit() {
    }

    public CacheHit(String cacheId, boolean isHit, String userId, String cacheType) {
        this.cacheId = cacheId;
        this.isHit = isHit;
        this.userId = userId;
        this.cacheType = cacheType;
        type = this.isHit ? "cache" : "API";
    }

}

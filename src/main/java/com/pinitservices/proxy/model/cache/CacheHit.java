/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model.cache;

import com.pinitservices.proxy.model.BasicEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

/**
 *
 * @author Ramdane
 */
@FieldNameConstants
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CacheHit extends BasicEntity {

    private String cacheId;
    /**
     * Hit means that returned from cache Non hit means returned from API
     */
    private boolean hit;

    private String userId;
    private String cachetype;
    private String type;

}

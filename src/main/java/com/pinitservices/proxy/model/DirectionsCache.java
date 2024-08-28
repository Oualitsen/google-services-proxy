/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model;

import com.pinitservices.proxy.googleApiModel.DirectionResult;
import com.pinitservices.proxy.model.geojson.GeoPoint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Ramdane
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@FieldNameConstants
@Document("DirectionsCache")
public class DirectionsCache extends Cache {

    private DirectionResult result;
    private boolean withTrafficInfo;

    private String lang;
    private long departureTime;


    private GeoPoint origin;

    private GeoPoint destination;

    public DirectionsCache() {
    }

    public DirectionsCache(DirectionResult result, boolean withTrafficInfo, String lang, long departureTime) {
        this.result = result;
        this.withTrafficInfo = withTrafficInfo;
        this.lang = lang;
        this.departureTime = departureTime;
    }

}

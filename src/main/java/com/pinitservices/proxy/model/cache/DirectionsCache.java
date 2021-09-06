/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model.cache;

import com.pinitservices.proxy.model.DirectionResult;
import com.pinitservices.proxy.model.geojson.GeoPoint;

/**
 *
 * @author Ramdane
 */

public class DirectionsCache extends Cache {

    public static final String FIELD_WITH_TRAFFIC = "withTrafficInfo";
    public static final String FIELD_LANG = "lang";
    public static final String FIELD_DEPARTURE_TIME = "departureTime";
    public static final String FIELD_RESULT = "result";
    public static final String FIELD_ORIGIN = "origin";
    public static final String FIELD_DESTINATION = "destination";

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

    public boolean isWithTrafficInfo() {
        return withTrafficInfo;
    }

    public void setWithTrafficInfo(boolean withTrafficInfo) {
        this.withTrafficInfo = withTrafficInfo;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public long getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(long departureTime) {
        this.departureTime = departureTime;
    }

    public DirectionResult getResult() {
        return result;
    }

    public void setResult(DirectionResult result) {
        this.result = result;
    }

    public GeoPoint getOrigin() {
        return origin;
    }

    public void setOrigin(GeoPoint origin) {
        this.origin = origin;
    }

    public GeoPoint getDestination() {
        return destination;
    }

    public void setDestination(GeoPoint destination) {
        this.destination = destination;
    }

}

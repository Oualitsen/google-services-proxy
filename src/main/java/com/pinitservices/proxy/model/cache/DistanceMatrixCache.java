/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model.cache;

import com.pinitservices.proxy.model.DistanceMatrixResponse;
import com.pinitservices.proxy.model.geojson.GeoPoint;

/**
 *
 * @author Ramdane
 */
public class DistanceMatrixCache extends Cache {

    public static final String FIELD_RESPONSE = "response";
    public static final String FIELD_ORIGINS = "origins";
    public static final String FIELD_DESTINATIONS = "destinations";
    public static final String FIELD_ORIGIN = "origin";
    public static final String FIELD_DESTINATION = "destination";
    public static final String FIELD_TIME = "time";
    public static final String FIELD_WITH_TRAFFIC = "withoutTraffic";

    private DistanceMatrixResponse response;
    private String origins;
    private String destinations;
    private long time;
    private boolean withoutTraffic;

    private GeoPoint origin;
    private GeoPoint destination;

    private String lang;

    public DistanceMatrixResponse getResponse() {
        return response;
    }

    public void setResponse(DistanceMatrixResponse response) {
        this.response = response;
    }

    public String getOrigins() {
        return origins;
    }

    public void setOrigins(String origins) {
        this.origins = origins;
        initPoints();
    }

    public String getDestinations() {
        return destinations;
    }

    public void setDestinations(String destinations) {
        this.destinations = destinations;
        initPoints();
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setWithoutTraffic(boolean withoutTraffic) {
        this.withoutTraffic = withoutTraffic;
    }

    public boolean isWithoutTraffic() {
        return withoutTraffic;
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

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    private void initPoints() {
        if (origins != null) {
            final String[] split = origins.replaceAll("\\|", ",").split(",");
            if (origin == null) {
                origin = new GeoPoint();
            }
            double[] coordinates = {Double.parseDouble(split[0]), Double.parseDouble(split[1])};
            origin.setCoordinates(coordinates);
        }

        if (destinations != null) {
            final String[] split = destinations.replaceAll("\\|", ",").split(",");
            if (destination == null) {
                destination = new GeoPoint();
            }
            double[] coordinates = {Double.parseDouble(split[0]), Double.parseDouble(split[1])};
            destination.setCoordinates(coordinates);
        }
    }

}

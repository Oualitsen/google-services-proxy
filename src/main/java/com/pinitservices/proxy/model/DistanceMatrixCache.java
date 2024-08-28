/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model;

import com.pinitservices.proxy.googleApiModel.DistanceMatrixResponse;
import com.pinitservices.proxy.model.geojson.GeoPoint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

/**
 * @author Ramdane
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@FieldNameConstants
public class DistanceMatrixCache extends Cache {

    private DistanceMatrixResponse response;
    private String origins;
    private String destinations;
    private long time;
    private boolean withoutTraffic;

    private GeoPoint origin;

    private GeoPoint destination;

    public void setOrigins(String origins) {
        this.origins = origins;
        initPoints();
    }

    public void setDestinations(String destinations) {
        this.destinations = destinations;
        initPoints();
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

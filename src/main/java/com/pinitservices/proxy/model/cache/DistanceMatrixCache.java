/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model.cache;

import com.pinitservices.proxy.model.DistanceMatrixResponse;
import com.pinitservices.proxy.model.geojson.GeoPoint;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 *
 * @author Ramdane
 */
@FieldNameConstants
@Getter
@Setter
public class DistanceMatrixCache extends Cache {

    private DistanceMatrixResponse response;
    private String origins;
    private String destinations;
    private long time;
    private boolean withoutTraffic;

    @GeoSpatialIndexed
    private GeoPoint origin;
    @GeoSpatialIndexed
    private GeoPoint destination;

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

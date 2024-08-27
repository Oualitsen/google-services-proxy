/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model.geojson;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import lombok.extern.slf4j.Slf4j;

/**
 *
 */

@Getter
@Setter
@Slf4j
@FieldNameConstants
public class GeoPoint extends BasicGeoShape {

    private double[] coordinates;

    public GeoPoint() {

        type = TYPE_POINT;
        coordinates = new double[2];

    }

    public GeoPoint(double lat, double lng) {
        this();
        coordinates[0] = lng;
        coordinates[1] = lat;
    }

    public double getLat() {
        return coordinates[1];
    }

    public void setLat(double lat) {
        coordinates[1] = lat;
    }

    public double getLng() {
        return coordinates[0];
    }

    public void setLng(double lng) {
        coordinates[0] = lng;
    }

}

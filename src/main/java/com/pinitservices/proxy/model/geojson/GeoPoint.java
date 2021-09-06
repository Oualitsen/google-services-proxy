/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model.geojson;

/**
 *
 *
 */
public class GeoPoint extends BasicGeoShape {

    private double[] coordinates;

    public GeoPoint() {
        type = TYPE_POINT;
    }

    public GeoPoint(double lat, double lng) {
        this();
        coordinates = new double[2];
        coordinates[0] = lng;
        coordinates[1] = lat;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    public String toUri() {
        return String.format("%s,%s", coordinates[1], coordinates[0]);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model.geojson;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

/**
 * @author Ramdane
 *
 */
@Getter
@Setter
@FieldNameConstants
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

    public double getLat() {
        return coordinates[1];
    }

    public double getLng() {
        return coordinates[0];
    }

    public String toUri() {
        return String.format("%s,%s", coordinates[1], coordinates[0]);
    }

    public static GeoPoint parse(String csv) {
        List<Double> list = List.of(csv.split(",")).stream().map(Double::parseDouble).toList();
        return new GeoPoint(list.get(0), list.get(1));
    }

}

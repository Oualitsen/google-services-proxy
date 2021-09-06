/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model.geojson;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 
 */

public class GeoPolygon extends BasicGeoShape {

    private List<List<Double[]>> coordinates;

    public GeoPolygon() {
        type = TYPE_POLYGON;
    }

    public void setCoordinates(List<List<Double[]>> coordinates) {
        this.coordinates = coordinates;
    }

    public List<List<Double[]>> getCoordinates() {
        return coordinates;
    }

    public void addCoordinates(double[] coords) {
        if (coordinates == null) {
            coordinates = new ArrayList<>();
            coordinates.add(new ArrayList<>());
        }

        coordinates.get(0).add(new Double[] { coords[0], coords[1] });

    }

}

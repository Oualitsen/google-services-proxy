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
public class GeoLine extends BasicGeoShape {

    private List<Double[]> coordinates;

    public List<Double[]> getCoordinates() {
        return coordinates;

    }

    public void setCoordinates(List<Double[]> coordinates) {
        this.coordinates = coordinates;
    }

    public void addCoordinates(double lng, double lat) {
        if (coordinates == null) {
            coordinates = new ArrayList<>();
        }

        coordinates.add(new Double[] { lng, lat });
    }

}

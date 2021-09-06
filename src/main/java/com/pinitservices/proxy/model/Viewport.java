/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model;

/**
 *
 * 
 */

public class Viewport {

    public static final String FIELD_NORTHEAST = "northeast";
    public static final String FIELD_SOUTHWEST = "southwest";

    private Coords northeast;

    private Coords southwest;

    public Coords getNortheast() {
        return northeast;
    }

    public void setNortheast(Coords northeast) {
        this.northeast = northeast;
    }

    public Coords getSouthwest() {
        return southwest;
    }

    public void setSouthwest(Coords southwest) {
        this.southwest = southwest;
    }

    public boolean contains(double lat, double lng) {
        return lat >= southwest.getLat() && lat <= northeast.getLat() && lng >= southwest.getLng()
                && lng <= northeast.getLng();
    }

}

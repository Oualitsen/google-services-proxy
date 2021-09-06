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

public class Coords {

    public static final String FIELD_LAT = "lat";
    public static final String FIELD_LNG = "lng";

    private double lat;
    private double lng;

    public Coords() {
    }

    public Coords(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

}

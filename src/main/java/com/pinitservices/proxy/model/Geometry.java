/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 *
 */

public class Geometry {

    public static final String FIELD_LOCATION = "location";
    public static final String FIELD_VIEWPORT = "viewport";
    public static final String FIELD_LOCATION_TYPE = "locationType";

    private Coords location;

    @JsonProperty("location_type")
    private String locationType;

    private Viewport viewport;

    public Coords getLocation() {
        return location;
    }

    public void setLocation(Coords location) {
        this.location = location;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public boolean contains(double lat, double lng) {
        return viewport.contains(lat, lng);
    }

}

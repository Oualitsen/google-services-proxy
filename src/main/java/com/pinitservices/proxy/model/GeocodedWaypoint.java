/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 *
 * @author Ramdane
 */

public class GeocodedWaypoint {

    @JsonProperty("geocoder_status")
    private GeocodedWaypointStatus status;

    @JsonProperty("place_id")
    private String placeId;

    private List<String> types;

    public GeocodedWaypointStatus getStatus() {
        return status;
    }

    public void setStatus(GeocodedWaypointStatus status) {
        this.status = status;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

}

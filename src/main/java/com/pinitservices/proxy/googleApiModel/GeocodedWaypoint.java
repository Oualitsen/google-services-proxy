/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.googleApiModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Ramdane
 */
@Getter
@Setter
public class GeocodedWaypoint {

    @JsonProperty("geocoder_status")
    private GeocodedWaypointStatus status;

    @JsonProperty("place_id")
    private String placeId;

    private List<String> types;

}

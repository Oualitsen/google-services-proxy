/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

/**
 *
 * @author Ramdane
 */
@FieldNameConstants
@Getter
@Setter
public class GeocodedWaypoint {

    @JsonProperty("geocoder_status")
    private GeocodedWaypointStatus status;

    @JsonProperty("place_id")
    private String placeId;

    private List<String> types;

}

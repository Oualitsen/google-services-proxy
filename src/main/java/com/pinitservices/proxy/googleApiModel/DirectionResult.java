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
 *
 */
@Getter
@Setter
public class DirectionResult {

    @JsonProperty("geocoded_waypoints")
    private List<GeocodedWaypoint> geocodedWaypoints;

    private DirectionResultStatus status;
    private List<Route> routes;


}

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
public class Route {

    private String copyrights;
    private Bounds bounds;
    private List<Leg> legs;

    @JsonProperty("overview_polyline")
    private Polyline overviewPolyline;

    private String summary;
}

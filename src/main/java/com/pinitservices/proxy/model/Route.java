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
public class Route {

    private String copyrights;
    private Bounds bounds;
    private List<Leg> legs;

    @JsonProperty("overview_polyline")
    private Polyline overviewPolyline;

    private String summary;

}

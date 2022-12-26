/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Step {

    private TextValue distance;
    private TextValue duration;
    @JsonProperty("start_location")
    private Coords startLocation;

    @JsonProperty("end_location")
    private Coords endLocation;

    private Polyline polyline;

    @JsonProperty("html_instructions")
    private String HtmlInstructions;

    @JsonProperty("travel_mode")
    private String travelMode;

}

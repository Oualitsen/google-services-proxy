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
public class Leg {

    private TextValue distance;
    private TextValue duration;

    @JsonProperty("start_address")
    private String startAddress;

    @JsonProperty("end_address")
    private String endAddress;

    @JsonProperty("start_location")
    private Coords startLocation;

    @JsonProperty("end_location")
    private Coords endLocation;

    private List<Step> steps;

}

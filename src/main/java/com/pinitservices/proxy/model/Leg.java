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

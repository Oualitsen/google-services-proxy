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

    public TextValue getDistance() {
        return distance;
    }

    public void setDistance(TextValue distance) {
        this.distance = distance;
    }

    public TextValue getDuration() {
        return duration;
    }

    public void setDuration(TextValue duration) {
        this.duration = duration;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public Coords getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Coords startLocation) {
        this.startLocation = startLocation;
    }

    public Coords getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Coords endLocation) {
        this.endLocation = endLocation;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

}

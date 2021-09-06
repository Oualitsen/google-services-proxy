/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Ramdane
 */

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

    public Polyline getPolyline() {
        return polyline;
    }

    public void setPolyline(Polyline polyline) {
        this.polyline = polyline;
    }

    public String getHtmlInstructions() {
        return HtmlInstructions;
    }

    public void setHtmlInstructions(String HtmlInstructions) {
        this.HtmlInstructions = HtmlInstructions;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

}

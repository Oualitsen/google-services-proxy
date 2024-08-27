/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

/**
 *
 */

@Getter
@Setter
@FieldNameConstants
@EqualsAndHashCode(of = {"longitude", "latitude"})
public class Coordinates {

    protected double longitude;
    protected double latitude;


    /**
     * The client time creation date
     */
    protected long localTime;
    /**
     * The server creation date
     */
    protected long serverTime;

    /**
     * GPS or NETWORK
     */
    private String provider;

    public Coordinates() {
        this(0, 0);
    }

    public Coordinates(double latitude, double longitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @JsonIgnore
    public String getUriFormat() {
        return String.format("%s,%s", latitude, longitude);
    }

}

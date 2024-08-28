/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.googleApiModel;

import lombok.Getter;
import lombok.Setter;


/**
 *
 */
@Getter
@Setter
public class Viewport {
    private Coords northeast;
    private Coords southwest;


    public boolean contains(Coords coordinates) {
        double lat = coordinates.lat();
        double lng = coordinates.lng();
        return lat >= southwest.lat() && lat <= northeast.lat()
                && lng >= southwest.lng() && lng <= northeast.lng();
    }

}

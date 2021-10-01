/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

/**
 *
 *
 */
@FieldNameConstants
@Getter
@Setter
public class Viewport {

    private Coords northeast;

    private Coords southwest;

    public boolean contains(double lat, double lng) {
        return lat >= southwest.getLat() && lat <= northeast.getLat() && lng >= southwest.getLng()
                && lng <= northeast.getLng();
    }

}

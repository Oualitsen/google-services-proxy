/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.googleApiModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;


/**
 *
 */

@Getter
@Setter
@FieldNameConstants
public class Geometry {


    private Coords location;

    @JsonProperty("location_type")
    private String locationType;

    private Viewport viewport;


    public boolean contains(Coords coordinates) {
        return viewport.contains(coordinates);
    }

}

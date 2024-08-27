/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.googleApiModel;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.util.List;

/**
 *
 */

@Getter
@Setter
@FieldNameConstants
public class GeocodeResponse {

    private List<GeocodeResult> results;
    private ResponseStatus status;

}

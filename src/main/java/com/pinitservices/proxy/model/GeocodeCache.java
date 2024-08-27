/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model;

import com.pinitservices.proxy.googleApiModel.GeocodeResponse;
import com.pinitservices.proxy.googleApiModel.GeocodeResult;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author Ramdane
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@FieldNameConstants
@Document()
public class GeocodeCache extends Cache {

    private GeocodeResponse response;

    private GeocodeResult result;

    public GeocodeCache(GeocodeResponse response) {
        this.response = response;
        final List<GeocodeResult> results = response.getResults();
        if (results != null && !results.isEmpty()) {
            result = results.get(0);
        }
    }


}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model.cache;

import java.util.List;

import com.pinitservices.proxy.model.GeocodeResponse;
import com.pinitservices.proxy.model.GeocodeResult;

/**
 *
 * @author Ramdane
 */

public class GeocodeCache extends Cache {

    public static final String FIELD_RESPONSE = "response";
    public static final String FIELD_RESULT = "result";

    private GeocodeResponse response;

    private GeocodeResult result;

    public GeocodeCache() {

    }

    public GeocodeCache(GeocodeResponse response) {
        this.response = response;
        final List<GeocodeResult> results = response.getResults();
        if (results != null && !results.isEmpty()) {
            result = results.get(0);
        }
    }

    public void setResponse(GeocodeResponse response) {
        this.response = response;
    }

    public GeocodeResponse getResponse() {
        return response;
    }

    public GeocodeResult getResult() {
        return result;
    }

    public void setResult(GeocodeResult result) {
        this.result = result;
    }

}

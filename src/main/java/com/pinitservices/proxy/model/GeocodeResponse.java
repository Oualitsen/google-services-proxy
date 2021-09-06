/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model;

import java.util.List;

/**
 *
 *
 */

public class GeocodeResponse {

    public static final String FIELD_RESULTS = "results";
    public static final String FIELD_STATUS = "status";

    private List<GeocodeResult> results;

    private ResponseStatus status;

    public List<GeocodeResult> getResults() {
        return results;
    }

    public void setResults(List<GeocodeResult> results) {
        this.results = results;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

}

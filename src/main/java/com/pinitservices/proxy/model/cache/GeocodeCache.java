/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model.cache;

import java.util.List;

import com.pinitservices.proxy.model.GeocodeResponse;
import com.pinitservices.proxy.model.GeocodeResult;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Ramdane
 */
@Document
@FieldNameConstants
@Getter
@Setter
@NoArgsConstructor
public class GeocodeCache extends Cache {

    private GeocodeResponse response;

    private GeocodeResult result;

    public GeocodeCache(GeocodeResponse response, String language) {
        this.response = response;
        this.lang = language;
        final List<GeocodeResult> results = response.getResults();
        if (results != null && !results.isEmpty()) {
            result = results.get(0);
        }
    }

}

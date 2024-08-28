package com.pinitservices.proxy.googleApiModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * IMPORTANT lng must come before lat (Mongodb craziness)
 *
 * @param lng
 * @param lat
 */
public record Coords(double lng, double lat) {

    @JsonIgnore
    public String getUriFormat() {
        return String.format("%s,%s", lat, lng);
    }
}



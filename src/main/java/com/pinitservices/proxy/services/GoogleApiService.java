/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.services;



import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

import com.pinitservices.proxy.googleApiModel.DirectionResult;
import com.pinitservices.proxy.googleApiModel.DistanceMatrixResponse;
import com.pinitservices.proxy.googleApiModel.GeocodeResponse;
import com.pinitservices.proxy.googleApiModel.PlacesResult;

/**
 * @author ramdane
 */
public interface GoogleApiService {

    @GetExchange("geocode/json")
    GeocodeResponse geocode(
            @RequestParam("place_id") String placeId,
            @RequestParam("key") String key);

    @GetExchange("geocode/json")
    GeocodeResponse reverseGeocode(
            @RequestParam("latlng") String latlng,
            @RequestParam("key") String key
    );

    @GetExchange("place/autocomplete/json")
    PlacesResult getPlaces(
            @RequestParam("input") String input,
            @RequestParam("key") String key,
            @RequestParam("language") String lang,
            @RequestParam("components") String components
    );

    @GetExchange("distancematrix/json?units=metric&traffic_model=best_guess&mode=driving")
    DistanceMatrixResponse getDistanceMatrix(
            @RequestParam("origins") String origins,
            @RequestParam("destinations") String destinations,
            @RequestParam("departure_time") String time,
            @RequestParam("key") String key,
            @RequestParam("language") String lang
    );

    @GetExchange("distancematrix/json?units=metric&mode=driving")
    DistanceMatrixResponse getDistanceMatrixWithoutTrafficInfo(
            @RequestParam("origins") String origins,
            @RequestParam("destinations") String destinations,
            @RequestParam("key") String key,
            @RequestParam("language") String lang
    );

    @GetExchange("directions/json?units=metric&traffic_model=best_guess&mode=driving&alternatives=true")
    DirectionResult getDirections(
            @RequestParam("origin") String origin,
            @RequestParam("destination") String destination,
            @RequestParam("departure_time") String time,
            @RequestParam("language") String lang,
            @RequestParam("key") String key
    );

    @GetExchange("directions/json?units=metric&mode=driving&alternatives=true")
    DirectionResult getDirectionsWithoutTraffocInfo(
            @RequestParam("origin") String origin,
            @RequestParam("destination") String destination,
            @RequestParam("language") String lang,
            @RequestParam("key") String key
    );

}

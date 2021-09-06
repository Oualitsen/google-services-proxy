/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.services;

import com.pinitservices.proxy.model.DirectionResult;
import com.pinitservices.proxy.model.DistanceMatrixResponse;
import com.pinitservices.proxy.model.GeocodeResponse;
import com.pinitservices.proxy.model.PlacesResult;
import com.pinitservices.proxy.model.cache.PlacesCache;
import com.pinitservices.proxy.model.geojson.GeoPoint;
import com.pinitservices.proxy.remote.RemoteApiService;
import com.pinitservices.proxy.repositories.PlacesCacheRepo;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 *
 * @author Ramdane
 */
@Service
public class RemoteApiServiceWithCache {

    @Autowired
    private Logger logger;

    @Autowired
    private RemoteApiService service;

    @Autowired
    private PlacesCacheRepo placesCacheRepo;

    public Mono<GeocodeResponse> geocode(String placeId) {

        return service.geocode(placeId);
    }

    public Mono<GeocodeResponse> reverceGeocode(String latlng) {
        return service.reverceGeocode(latlng);
    }

    public Mono<PlacesResult> getPlaces(String input, String lang, String components) {
        return placesCacheRepo.findFirstByQueryAndLang(input, lang).map(PlacesCache::getResult)
                .switchIfEmpty(Mono.defer(() -> service.getPlaces(input, lang, components)));

    }

    public Mono<DistanceMatrixResponse> getDistanceMatrix(String origins, String destinations, long time, String lang) {
        return service.getDistanceMatrix(origins, destinations, time, lang);
    }

    public Mono<DistanceMatrixResponse> getDistanceMatrixWithoutTrafficInfo(String origins, String destinations,
            String lang) {
        return service.getDistanceMatrixWithoutTrafficInfo(origins, destinations, lang);
    }

    public Mono<DirectionResult> getDirections(GeoPoint origin, GeoPoint destination, boolean withTraffic, long time,
            String lang) {
        return service.getDirections(origin, destination, withTraffic, time, lang);
    }

    public Mono<DirectionResult> getDirectionsWithoutTraffocInfo(GeoPoint orgin, GeoPoint destination, long time,
            String lang, String key) {
        return service.getDirectionsWithoutTraffocInfo(orgin, destination, time, lang, key);
    }

}

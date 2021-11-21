/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.services;

import lombok.extern.java.Log;

import com.pinitservices.proxy.model.DirectionResult;
import com.pinitservices.proxy.model.DistanceMatrixResponse;
import com.pinitservices.proxy.model.GeocodeResponse;
import com.pinitservices.proxy.model.PlacesResult;
import com.pinitservices.proxy.model.cache.DirectionsCache;
import com.pinitservices.proxy.model.cache.DistanceMatrixCache;
import com.pinitservices.proxy.model.cache.GeocodeCache;
import com.pinitservices.proxy.model.cache.PlacesCache;
import com.pinitservices.proxy.model.geojson.GeoPoint;
import com.pinitservices.proxy.remote.RemoteApiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 *
 * @author Ramdane
 */
@Log
@Service
public class RemoteApiServiceWrapper {

    @Value("${cacheEnabled}")
    private boolean enabled;

    @Autowired
    private RemoteApiService service;

    @Autowired
    private PlacesCacheService placesCacheService;

    @Autowired
    private DistanceMatrixCacheService distanceMatrixCacheService;

    @Autowired
    private DirectionsCacheService directionsCacheService;

    @Autowired
    private GeocodeCacheService geocodeCacheService;

    public Mono<GeocodeResponse> geocode(String placeId, String language) {

        return geocodeCacheService.findCache(placeId, language)
                .map(GeocodeCache::getResponse)
                .switchIfEmpty(Mono.defer(() -> service.geocode(placeId, language)
                .flatMap(e -> geocodeCacheService.cache(e, language)
                ).map(GeocodeCache::getResponse)));

    }

    public Mono<GeocodeResponse> reverceGeocode(double lat, double lng, String language) {
        return geocodeCacheService.findCache(lat, lng, language)
                .map(GeocodeCache::getResponse)
                .switchIfEmpty(
                        Mono.defer(()
                                -> service.reverceGeocode(String.format("%s,%s", lat, lng), language)
                                .flatMap(r
                                        -> geocodeCacheService.cache(r, language)
                                ).map(GeocodeCache::getResponse)
                        ));

    }

    public Mono<PlacesResult> getPlaces(String input, String lang, String components) {
        return placesCacheService.findCache(input, lang)
                .map(PlacesCache::getResult)
                .switchIfEmpty(Mono.defer(() -> service.getPlaces(input, lang, components)
                .flatMap(r -> placesCacheService.cache(r, input, lang).map(PlacesCache::getResult))));

    }

    public Mono<DistanceMatrixResponse> getDistanceMatrix(GeoPoint origin, GeoPoint destination, boolean traffic, long time, String lang) {

        return distanceMatrixCacheService.findCache(origin, destination, lang, traffic, time)
                .map(e -> {

                    log.info("from cache");
                    return e;
                })
                .map(DistanceMatrixCache::getResponse)
                .switchIfEmpty(
                        Mono.defer(() -> service.getDistanceMatrix(origin.toUri(),
                        destination.toUri(),
                        time, lang, traffic)
                        .flatMap(
                                e -> distanceMatrixCacheService.cache(e,
                                        origin,
                                        destination,
                                        lang, time, traffic)
                        ).map((DistanceMatrixCache::getResponse))
                        )
                );

    }

    public Mono<DirectionResult> getDirections(GeoPoint origin, GeoPoint destination, boolean withTraffic, long time,
            String lang) {
        return directionsCacheService.findCache(origin, destination, lang, withTraffic, time)
                .map(DirectionsCache::getResult)
                .switchIfEmpty(Mono.defer(() -> service.getDirections(origin, destination, withTraffic, time, lang)
                .flatMap(r -> directionsCacheService.cahce(r, origin, destination, time, lang, withTraffic)).map(DirectionsCache::getResult)));

    }

}

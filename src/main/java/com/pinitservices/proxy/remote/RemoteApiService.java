/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.remote;

import com.pinitservices.proxy.model.DirectionResult;
import com.pinitservices.proxy.model.DistanceMatrixResponse;
import com.pinitservices.proxy.model.GeocodeResponse;
import com.pinitservices.proxy.model.PlacesResult;
import com.pinitservices.proxy.model.cache.DirectionsCache;
import com.pinitservices.proxy.model.cache.DistanceMatrixCache;
import com.pinitservices.proxy.model.cache.GeocodeCache;
import com.pinitservices.proxy.model.cache.PlacesCache;
import com.pinitservices.proxy.model.geojson.GeoPoint;
import com.pinitservices.proxy.repositories.DistanceMatrixCacheRepo;
import com.pinitservices.proxy.services.DirectionsCacheService;
import com.pinitservices.proxy.services.DistanceMatrixCacheService;
import com.pinitservices.proxy.services.GeocodeCacheService;
import com.pinitservices.proxy.services.PlacesCacheService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

/**
 *
 * @author Ramdane
 */
@Log
@Component
public class RemoteApiService {

    private final WebClient webClient;

    @Autowired
    private PlacesCacheService placesCacheService;

    @Autowired
    private DistanceMatrixCacheService distanceMatrixCacheService;

    @Autowired
    private DirectionsCacheService directionsCacheService;

    @Autowired
    private GeocodeCacheService geocodeCacheService;

    public RemoteApiService(@Autowired WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<GeocodeResponse> geocode(String placeId, String language) {

        return webClient.get()
                .uri(uri -> uri.path("geocode/json")
                .queryParam("place_id", placeId)
                .queryParam(language, language)
                .build())
                .retrieve()
                .bodyToMono(GeocodeResponse.class)
                .flatMap(e -> geocodeCacheService.cache(e, language))
                .map(GeocodeCache::getResponse);

    }

    public Mono<GeocodeResponse> reverceGeocode(
            String latlng, String language
    ) {
        return webClient.get()
                .uri(uri -> uri.path("geocode/json")
                .queryParam("latlng", latlng)
                .queryParam(language, language)
                .build())
                .retrieve()
                .bodyToMono(GeocodeResponse.class)
                .flatMap(e -> geocodeCacheService.cache(e, language))
                .map(GeocodeCache::getResponse);
    }

    public Mono<PlacesResult> getPlaces(
            String input,
            String lang,
            String components
    ) {

        return webClient.get()
                .uri(uri -> uri.path("place/autocomplete/json")
                .queryParam("input", input)
                .queryParam("lang", lang)
                .queryParam("components", components)
                .build())
                .retrieve()
                .bodyToMono(PlacesResult.class)
                .flatMap(r -> placesCacheService.cache(r, input, lang))
                .map(PlacesCache::getResult);

    }

    public Mono<DistanceMatrixResponse> getDistanceMatrix(
            String origins,
            String destinations,
            long time,
            String lang
    ) {

        return webClient.get()
                .uri(uri
                        -> uri.path("distancematrix/json")
                        .queryParam("origins", origins)
                        .queryParam("destinations", destinations)
                        .queryParam("language", lang)
                        .queryParam("units", "metric")
                        .queryParam("traffic_model", "best_guess")
                        .queryParam("departure_time", getTimeParam(time))
                        .queryParam("mode", "driving").build()
                )
                .retrieve()
                .bodyToMono(DistanceMatrixResponse.class)
                .flatMap(r -> distanceMatrixCacheService.cache(r, origins, destinations, lang))
                .map(DistanceMatrixCache::getResponse);
    }

    public Mono<DistanceMatrixResponse> getDistanceMatrixWithoutTrafficInfo(
            String origins,
            String destinations,
            String lang
    ) {
        return getDistanceMatrix(origins, destinations, 0, lang);
    }

    public Mono<DirectionResult> getDirections(
            GeoPoint origin,
            GeoPoint destination,
            boolean withTraffic,
            long time,
            String lang
    ) {
        return webClient.get()
                .uri(uri -> {
                    final UriBuilder queryParam = uri.path("directions/json")
                            .queryParam("origin", origin.toUri())
                            .queryParam("destination", destination.toUri())
                            .queryParam("language", lang)
                            .queryParam("units", "metric")
                            .queryParam("mode", "driving")
                            .queryParam("departure_time", getTimeParam(time))
                            .queryParam("alternatives", "true");

                    if (withTraffic) {
                        queryParam.queryParam("traffic_model", "best_guess");
                    }

                    return queryParam.build();
                })
                .retrieve()
                .bodyToMono(DirectionResult.class)
                .flatMap(dr -> directionsCacheService.cahce(dr, time, lang, origin, destination, withTraffic))
                .map(DirectionsCache::getResult);

    }

    public Mono<DirectionResult> getDirectionsWithoutTraffocInfo(
            GeoPoint orgin,
            GeoPoint destination,
            long time,
            String lang,
            String key
    ) {
        return getDirections(orgin, destination, false, time, lang);
    }

    private String getTimeParam(long time) {
        return time == 0 ? "now" : String.valueOf(time);
    }
}

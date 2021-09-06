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
import com.pinitservices.proxy.model.cache.PlacesCache;
import com.pinitservices.proxy.model.geojson.GeoPoint;
import com.pinitservices.proxy.repositories.DirectionsCacheRepo;
import com.pinitservices.proxy.repositories.DistanceMatrixCacheRepo;
import com.pinitservices.proxy.repositories.PlacesCacheRepo;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

/**
 *
 * @author Ramdane
 */
@Component
public class RemoteApiService {

    private WebClient webClient;

    @Autowired
    private PlacesCacheRepo placesCacheRepo;

    @Autowired
    private DistanceMatrixCacheRepo distanceMatrixCacheRepo;

    @Autowired
    private DirectionsCacheRepo directionsCacheRepo;

    @Autowired
    private Logger logger;

    public RemoteApiService(@Autowired WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<GeocodeResponse> geocode(String placeId) {

        return webClient.get()
                .uri(uri -> uri.path("geocode/json")
                .queryParam("place_id", placeId)
                .build())
                .retrieve()
                .bodyToMono(GeocodeResponse.class);

    }

    public Mono<GeocodeResponse> reverceGeocode(
            String latlng
    ) {
        return webClient.get()
                .uri(uri -> uri.path("geocode/json")
                .queryParam("latlng", latlng)
                .build())
                .retrieve()
                .bodyToMono(GeocodeResponse.class);
    }

    public Mono<PlacesResult> getPlaces(
            String input,
            String lang,
            String components
    ) {

        logger.info("getting places from network@@");

        return webClient.get()
                .uri(uri -> uri.path("place/autocomplete/json")
                .queryParam("input", input)
                .queryParam("lang", lang)
                .queryParam("components", components)
                .build())
                .retrieve()
                .bodyToMono(PlacesResult.class)
                .doOnNext(r -> {

                    switch (r.getStatus()) {
                        case OK, ZERO_RESULTS -> {
                            cache(r, input, lang);
                        }
                    }

                });
    }

    private void cache(PlacesResult r, String input, String lang) {
        var cache = new PlacesCache();
        cache.setLang(lang);
        cache.setQuery(input.toLowerCase());
        cache.setResult(r);
        placesCacheRepo.insert(cache).subscribe();
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
                .doOnNext(r -> {
                    cache(r, origins, destinations, lang);
                });
    }

    private void cache(DistanceMatrixResponse r, String origins, String destinations, String lang) {
        var cache = new DistanceMatrixCache();
        cache.setLang(lang);
        cache.setDestinations(destinations);
        cache.setOrigins(origins);
        cache.setResponse(r);
        distanceMatrixCacheRepo.insert(cache).subscribe();
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
                .doOnNext(dr -> {
                    switch (dr.getStatus()) {
                        case OK, ZERO_RESULTS -> {
                            cahce(dr, time, lang, origin, destination, withTraffic);
                        }
                    }

                });
    }

    private void cahce(DirectionResult dr, long time,
            String lang,
            GeoPoint origin,
            GeoPoint destination,
            boolean withTrafficInfo) {
        DirectionsCache dc = new DirectionsCache();
        dc.setLang(lang);
        dc.setDepartureTime(time);
        dc.setDestination(destination);
        dc.setOrigin(origin);
        dc.setWithTrafficInfo(withTrafficInfo);
        dc.setResult(dr);

        directionsCacheRepo.save(dc).subscribe();
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

package com.pinitservices.proxy.remote;

import com.pinitservices.proxy.model.DirectionResult;
import com.pinitservices.proxy.model.DistanceMatrixResponse;
import com.pinitservices.proxy.model.GeocodeResponse;
import com.pinitservices.proxy.model.PlacesResult;
import com.pinitservices.proxy.model.geojson.GeoPoint;
import lombok.extern.java.Log;
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

    public RemoteApiService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<GeocodeResponse> geocode(String placeId, String language) {

        return webClient.get()
                .uri(uri -> uri.path("geocode/json").queryParam("place_id", placeId)
                .queryParam(language, language).build())
                .retrieve().bodyToMono(GeocodeResponse.class);

    }

    public Mono<GeocodeResponse> reverceGeocode(String latlng, String language) {
        return webClient.get()
                .uri(uri -> uri.path("geocode/json").queryParam("latlng", latlng)
                .queryParam(language, language).build())
                .retrieve().bodyToMono(GeocodeResponse.class);
    }

    public Mono<PlacesResult> getPlaces(String input, String lang, String components) {

        return webClient.get()
                .uri(uri -> uri.path("place/autocomplete/json").queryParam("input", input)
                .queryParam("lang", lang).queryParam("components", components).build())
                .retrieve().bodyToMono(PlacesResult.class);

    }

    public Mono<DistanceMatrixResponse> getDistanceMatrix(String origins, String destinations, long time,
            String lang, boolean traffic) {

        return webClient.get()
                .uri(uri -> {
                    var builder = uri.path("distancematrix/json").queryParam("origins", origins)
                            .queryParam("destinations", destinations).queryParam("language", lang)
                            .queryParam("units", "metric").queryParam("traffic_model", "best_guess")
                            .queryParam("departure_time", getTimeParam(time))
                            .queryParam("mode", "driving");

                    if (traffic) {
                        builder.queryParam("traffic_model", "best_guess");
                    }

                    return builder.build();
                })
                .retrieve().bodyToMono(DistanceMatrixResponse.class);
    }

    public Mono<DirectionResult> getDirections(GeoPoint origin, GeoPoint destination, boolean withTraffic,
            long time, String lang) {
        return webClient.get().uri(uri -> {
            final UriBuilder queryParam = uri.path("directions/json").queryParam("origin", origin.toUri())
                    .queryParam("destination", destination.toUri()).queryParam("language", lang)
                    .queryParam("units", "metric").queryParam("mode", "driving")
                    .queryParam("departure_time", getTimeParam(time))
                    .queryParam("alternatives", "true");

            if (withTraffic) {
                queryParam.queryParam("traffic_model", "best_guess");
            }

            return queryParam.build();
        }).retrieve().bodyToMono(DirectionResult.class);

    }

    public static String getTimeParam(long time) {
        return time <= 0 ? "now" : String.valueOf(time);
    }
}

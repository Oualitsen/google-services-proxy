package com.pinitservices.proxy.services;

import com.pinitservices.proxy.exceptions.InvalidDistanceMatrixResult;
import com.pinitservices.proxy.googleApiModel.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author ramdane
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GoogleApiServiceWrapper {

    private final String lang = "en";
    private final GoogleApiService service;
    private final DistanceMatrixCacheService cacheCollection;
    private final PlacesCacheService placesCacheService;
    private final GeocodeCacheService geocodeCacheService;
    private final DirectionsCacheService directionsCacheService;

    @Value("${google.api.key}")
    private String apiKey;

    public GeocodeResponse geocode(String placeId, String userId) {
        var result = geocodeCacheService.findCache(placeId, userId);
        if (result == null) {
            result = service.geocode(placeId, apiKey);
            geocodeCacheService.cache(result, userId);
        }
        return result;

    }

    public GeocodeResponse reverseGeocode(String latLng, String userId) {
        String[] split = latLng.split(",");
        var result = geocodeCacheService.findCache(Double.parseDouble(split[0]), Double.parseDouble(split[1]), userId);
        if (result == null) {
            result = service.reverseGeocode(latLng, apiKey);
            geocodeCacheService.cache(result, userId);
        }
        return result;

    }

    public GeocodeResponse reverseGeocode(Coords coords, String userId) {
        return reverseGeocode(coords.getUriFormat(), userId);
    }

    public PlacesResult getPlaces(String input, String lang, String userId) {

        log.info("API KEY = " + apiKey);
        var result = placesCacheService.findCache(input, lang, userId);
        if (result == null) {
            result = service.getPlaces(input, apiKey, lang, "country:dz");
            placesCacheService.cache(result, input, lang, userId);
        }
        return result;
    }

    public PlacesResult getPlaces(String input, String userId) {
        return getPlaces(input, lang, userId);
    }

    public DistanceMatrixResponse getDistanceMatrix(
            Coords origins,
            Coords destinations,
            String time,
            String lang,
            final boolean check,
            final boolean traffic,
            String userId) {

        if (origins.equals(destinations)) {
            DistanceMatrixResponse response = new DistanceMatrixResponse();
            response.setStatus(ResponseStatus.OK);
            Element element = new Element();
            element.setStatus(ElementStatus.OK);
            element.setDistance(new TextValue("1 min", 0));
            element.setDistance(new TextValue("1 m", 0));
            element.setDurationInTraffic(new TextValue("1 min", 0));
            Row row = new Row();
            row.setElements(List.of(element));
            response.setRows(List.of(row));
            return response;
        }
        var cache = cacheCollection.findCache(origins, destinations, true, userId);
        if (cache != null) {
            return cache;
        }
        var resp = service.getDistanceMatrix(origins.getUriFormat(),
                destinations.getUriFormat(),
                time, apiKey,
                lang);
        cacheCollection.cache(resp, origins, destinations, time, true, userId);
        if (check) {
            if (resp.getRows().stream().filter(
                            r -> r.getElements().stream().filter(elem -> elem.getStatus() != ElementStatus.OK).count() == 0)
                    .count() == 0) {
                return resp;
            } else {
                throw new InvalidDistanceMatrixResult(resp);
            }
        } else {
            return resp;
        }

    }

    public DistanceMatrixResponse getPath(
            List<Coords> orgins,
            String userId) {
        return getPath(orgins, lang, userId);
    }

    public DistanceMatrixResponse getPath(
            List<Coords> orgins,
            long departureTime,
            String userId) {
        return getPath(orgins, departureTime, lang, userId);
    }

    public DistanceMatrixResponse getPath(
            List<Coords> orgins,
            String lang,
            String userId) {
        return getPath(orgins, 0, lang, userId);
    }

    public DistanceMatrixResponse getPath(
            List<Coords> path,
            long time,
            String lang,
            String userId) {

        List<DistanceMatrixResponse> list = new ArrayList<>();
        final Iterator<Coords> it = path.iterator();
        var orig = it.next();
        while (it.hasNext()) {
            var dest = it.next();
            list.add(getDistanceMatrix(orig, dest, time, lang, userId));
            orig = dest;
        }
        DistanceMatrixResponse matrix = new DistanceMatrixResponse();
        list.forEach(matrix::append);
        return matrix;

    }

    public DistanceMatrixResponse getDistanceMatrix(
            Coords orgins,
            Coords destinations,
            String lang,
            String userId) {
        return getDistanceMatrix(orgins, destinations, 0, lang, userId);
    }

    public DistanceMatrixResponse getDistanceMatrix(
            Coords orgins,
            Coords destinations,
            long time,
            String lang,
            String userId) {
        String when = time <= System.currentTimeMillis() ? "now" : time + "";
        return getDistanceMatrix(orgins, destinations, when, lang, false, false, userId);
    }

    public DistanceMatrixResponse getDistanceMatrix(
            Coords origins,
            Coords destinations,
            String userId) {
        return getDistanceMatrix(origins, destinations, 0, lang, userId);
    }



    public DirectionResult getDirections(
            final Coords origin,
            final Coords destination,
            boolean withTrafficInfo,
            String userId) {
        return getDirections(origin, destination, 0, lang, withTrafficInfo, userId);
    }

    public DirectionResult getDirections(
            final Coords origin,
            final Coords destination,
            long time,
            boolean withTrafficInfo,
            String userId) {
        return getDirections(origin, destination, time, lang, withTrafficInfo, userId);
    }

    public DirectionResult getDirections(
            final Coords origin,
            final Coords destination,
            long time,
            String lang,
            boolean withTrafficInfo,
            String userId) {
        var cache = directionsCacheService.findCache(origin, destination, lang, withTrafficInfo, userId);
        if (cache != null) {
            return cache;
        }
        final String key = apiKey;
        final String departureTime = time == 0 ? "now" : String.valueOf(time);
        DirectionResult result;
        if (withTrafficInfo) {
            result = service.getDirections(origin.getUriFormat(),
                    destination.getUriFormat(), departureTime, lang, key);
        } else {
            result = service.getDirectionsWithoutTraffocInfo(origin.getUriFormat(), destination.getUriFormat(), lang,
                    key);

        }
        directionsCacheService.cache(result, origin, destination, withTrafficInfo, lang, time, userId);
        return result;

    }

}

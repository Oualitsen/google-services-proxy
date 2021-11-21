package com.pinitservices.proxy.controllers;

import com.pinitservices.proxy.model.Coords;
import com.pinitservices.proxy.model.DirectionResult;
import com.pinitservices.proxy.model.DistanceMatrixResponse;
import com.pinitservices.proxy.model.cache.DistanceMatrixCache;
import com.pinitservices.proxy.model.geojson.GeoPoint;
import com.pinitservices.proxy.services.DirectionsCacheService;
import com.pinitservices.proxy.services.RemoteApiServiceWrapper;
import com.pinitservices.proxy.services.repositories.DistanceMatrixCacheRepository;
import java.util.List;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 * @author Ramdane
 */
@Log
@RestController
@RequestMapping("distance")
public class DistanceController {

    @Autowired
    private RemoteApiServiceWrapper service;

    @Autowired
    private DistanceMatrixCacheRepository repository;

    @PostMapping
    public Mono<DistanceMatrixResponse> getDistance(@RequestBody List<Coords> list,
            @RequestParam(value = "lang", defaultValue = "en") String lang,
            @RequestParam(value = "traffic", defaultValue = "false") boolean traffic,
            @RequestParam(value = "when", defaultValue = "-1") long when
    ) {

        var origin = new GeoPoint(list.get(0).getLat(), list.get(0).getLng());
        var destination = new GeoPoint(list.get(1).getLat(), list.get(1).getLng());

        return service.getDistanceMatrix(origin, destination, traffic, when, lang);

    }

    @PostMapping("test")
    public Flux<DistanceMatrixCache> test(@RequestBody List<Coords> list, @RequestParam(value = "lang", defaultValue = "en") String lang,
            @RequestParam(value = "traffic", defaultValue = "false") boolean traffic,
            @RequestParam(value = "when", defaultValue = "-1") long when) {

        var origin = new GeoPoint(list.get(0).getLat(), list.get(0).getLng());

        return repository.findCacheOrigin(lang, origin.getLat(), origin.getLng(), 300, traffic);

    }

}

package com.pinitservices.proxy.controllers;

import com.pinitservices.proxy.model.Coords;
import com.pinitservices.proxy.model.DirectionResult;
import com.pinitservices.proxy.model.geojson.GeoPoint;
import com.pinitservices.proxy.services.RemoteApiServiceWrapper;
import java.util.List;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 *
 * @author Ramdane
 */
@Log
@RestController
@RequestMapping("/maps/directions")
public class DirectionController {

    @Autowired
    private RemoteApiServiceWrapper service;

    @PostMapping
    public Mono<DirectionResult> getDirections(@RequestBody List<Coords> list,
            @RequestParam(value = "lang", defaultValue = "en") String lang,
            @RequestParam(value = "traffic", defaultValue = "false") boolean traffic,
            @RequestParam(value = "when", defaultValue = "-1") long when
    ) {

        var origin = new GeoPoint(list.get(0).getLat(), list.get(0).getLng());
        var destination = new GeoPoint(list.get(1).getLat(), list.get(1).getLng());

        return service.getDirections(origin, destination, traffic, when, lang);

    }

}

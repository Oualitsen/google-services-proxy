package com.pinitservices.proxy.controllers;

import com.pinitservices.proxy.model.Coords;
import com.pinitservices.proxy.model.PlacesResult;
import com.pinitservices.proxy.model.ResponseStatus;
import com.pinitservices.proxy.services.RemoteApiServiceWrapper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@Log
@RestController
@RequestMapping("/maps/places")
public class PlacesController {

    @Autowired
    private RemoteApiServiceWrapper service;

    @PostMapping()
    public Mono<PlacesResult> getPlaces(@RequestBody String request,
            @RequestParam(value = "lang", defaultValue = "en") String lang,
            @RequestParam(value = "component", required = false) String component) {
        log.info("request = " + request);
        return service.getPlaces(request, lang, component);

    }

    @PostMapping("reverse-geocode")
    public Mono<String> reverseGeocode(@RequestBody double[] array,
            @RequestParam(value = "lang", defaultValue = "en") String lang) {

        return service.reverceGeocode(array[0], array[1], lang)
                .map(r -> r.getResults().get(0).getFormattedAddress());

    }

    @GetMapping("get-place/{placeId}")
    public Mono<Coords> getPlace(@PathVariable String placeId, @RequestParam(value = "lang", defaultValue = "en") String lang) {
        return service.geocode(placeId, lang).filter(r -> r.getStatus() == ResponseStatus.OK)
                .map(r -> r.getResults().get(0).getGeometry().getLocation());

    }

}

package com.pinitservices.proxy.controllers;

import com.pinitservices.proxy.model.PlacesResult;
import com.pinitservices.proxy.services.RemoteApiServiceWithCache;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("places")
public class PlacesController {

    @Autowired
    private Logger logger;

    @Autowired
    private RemoteApiServiceWithCache service;

    @GetMapping()
    public Mono<String> test(@RequestParam(required = false) String key) {
        logger.info("testing ########@@@ " + key);
        return Mono.just("{'azul':'fellawen'}");
    }

    @GetMapping("{input}")
    public Mono<PlacesResult> test2(@PathVariable String input) {
        logger.info("removed blocking");
        return service.getPlaces(input, "fr", "country:dz");
    }

}

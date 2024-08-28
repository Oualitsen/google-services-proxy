package com.pinitservices.proxy.controllers;

import com.pinitservices.proxy.googleApiModel.Coords;
import com.pinitservices.proxy.googleApiModel.DistanceMatrixResponse;
import com.pinitservices.proxy.repositories.DistanceMatrixCacheRepository;
import com.pinitservices.proxy.services.GoogleApiServiceWrapper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ramdane
 */
@Log
@RestController
@RequestMapping("/maps/distance")
public class DistanceController {

    @Autowired
    private GoogleApiServiceWrapper service;

    @Autowired
    private DistanceMatrixCacheRepository repository;

    @PostMapping
    public DistanceMatrixResponse getDistance(@RequestBody List<Coords> list,
                                              @RequestParam(value = "lang", defaultValue = "en") String lang,
                                              @RequestParam(value = "traffic", defaultValue = "false") boolean traffic,
                                              @RequestParam(value = "when", defaultValue = "-1") long when) {

        var origin = list.get(0);
        var destination = list.get(1);

        return service.getDistanceMatrix(origin, destination, when + "", lang, true, traffic, "null");

    }


}

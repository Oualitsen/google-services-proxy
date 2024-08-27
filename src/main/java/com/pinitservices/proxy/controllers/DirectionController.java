package com.pinitservices.proxy.controllers;

import com.pinitservices.proxy.googleApiModel.Coords;
import com.pinitservices.proxy.googleApiModel.DirectionResult;
import com.pinitservices.proxy.model.geojson.GeoPoint;
import com.pinitservices.proxy.services.GoogleApiServiceWrapper;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Ramdane
 */
@Log
@RestController
@RequestMapping("/maps/directions")
@RequiredArgsConstructor
public class DirectionController {

    private final GoogleApiServiceWrapper service;

    @PostMapping
    public DirectionResult getDirections(@RequestBody List<Coords> list,
            @RequestParam(value = "lang", defaultValue = "en") String lang,
            @RequestParam(value = "traffic", defaultValue = "false") boolean traffic,
            @RequestParam(value = "when", defaultValue = "-1") long when
    ) {

        var origin = new Coords(list.get(0).lat(), list.get(0).lng());
        var destination = new Coords(list.get(1).lat(), list.get(1).lng());

        return service.getDirections(origin, destination, when, lang, traffic, "");

    }

}

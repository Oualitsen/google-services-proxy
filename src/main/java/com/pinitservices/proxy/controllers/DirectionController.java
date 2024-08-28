package com.pinitservices.proxy.controllers;

import com.pinitservices.proxy.googleApiModel.Coords;
import com.pinitservices.proxy.googleApiModel.DirectionResult;
import com.pinitservices.proxy.services.GoogleApiServiceWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
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

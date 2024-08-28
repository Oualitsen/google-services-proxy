package com.pinitservices.proxy.controllers;

import com.pinitservices.proxy.googleApiModel.Coords;
import com.pinitservices.proxy.googleApiModel.PlacesResult;
import com.pinitservices.proxy.googleApiModel.ResponseStatus;
import com.pinitservices.proxy.services.GoogleApiServiceWrapper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Log
@RestController
@RequestMapping("/maps/places")
public class PlacesController {

    @Autowired
    private GoogleApiServiceWrapper service;

    @PostMapping()
    public PlacesResult getPlaces(@RequestBody String request,
                                  @RequestParam(value = "lang", defaultValue = "en") String lang,
                                  @RequestParam(value = "component", required = false) String component) {
        log.info("request = " + request);
        return service.getPlaces(request, lang, component);

    }

    @PostMapping("reverse-geocode")
    public String reverseGeocode(@RequestBody double[] array,
                                 @RequestParam(value = "lang", defaultValue = "en") String lang) {

        var result = service.reverseGeocode(new Coords(array[0], array[1]), lang);
        if (result.getStatus() == ResponseStatus.OK) {
            return result.getResults().get(0).getFormattedAddress();
        }
        throw new RuntimeException("Could not reverse georcode coordinates " + array[0] + ", " + array[1]);

    }

    @GetMapping("get-place/{placeId}")
    public Coords getPlace(@PathVariable String placeId,
                           @RequestParam(value = "lang", defaultValue = "en") String lang) {
        var result = service.geocode(placeId, lang);

        if (result.getStatus() == ResponseStatus.OK) {
            return result.getResults().get(0).getGeometry().getLocation();
        }
        throw new RuntimeException("Could find place id = " + placeId);

    }

}

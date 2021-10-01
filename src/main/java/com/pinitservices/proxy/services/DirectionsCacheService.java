/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pinitservices.proxy.services;

import com.pinitservices.proxy.model.DirectionResult;
import com.pinitservices.proxy.model.cache.DirectionsCache;
import com.pinitservices.proxy.model.geojson.GeoPoint;
import com.pinitservices.proxy.repositories.DirectionsCacheRepo;
import lombok.experimental.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 *
 * @author Ramdane
 */
@Service
public class DirectionsCacheService implements DirectionsCacheRepo {

    @Autowired
    @Delegate
    private DirectionsCacheRepo repo;

    public Mono<DirectionsCache> cahce(DirectionResult dr, long time,
            String lang,
            GeoPoint origin,
            GeoPoint destination,
            boolean withTrafficInfo) {

        DirectionsCache dc = new DirectionsCache();
        dc.setLang(lang);
        dc.setDepartureTime(time);
        dc.setDestination(destination);
        dc.setOrigin(origin);
        dc.setWithTrafficInfo(withTrafficInfo);
        dc.setResult(dr);
        return save(dc);

    }

}

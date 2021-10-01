/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pinitservices.proxy.services;

import com.pinitservices.proxy.model.DistanceMatrixResponse;
import com.pinitservices.proxy.model.cache.DistanceMatrixCache;
import com.pinitservices.proxy.repositories.DistanceMatrixCacheRepo;
import lombok.experimental.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 *
 * @author Ramdane
 */
@Service
public class DistanceMatrixCacheService implements DistanceMatrixCacheRepo {

    @Delegate
    @Autowired
    private DistanceMatrixCacheRepo repo;

    public Mono<DistanceMatrixCache> cache(DistanceMatrixResponse r, String origins, String destinations, String lang) {
        var cache = new DistanceMatrixCache();
        cache.setLang(lang);
        cache.setDestinations(destinations);
        cache.setOrigins(origins);
        cache.setResponse(r);
        return save(cache);
    }
}

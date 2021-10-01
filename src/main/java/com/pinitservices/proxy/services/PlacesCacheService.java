/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pinitservices.proxy.services;

import com.pinitservices.proxy.model.PlacesResult;
import com.pinitservices.proxy.model.cache.PlacesCache;
import com.pinitservices.proxy.repositories.PlacesCacheRepo;
import lombok.experimental.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 *
 * @author Ramdane
 */
@Service
public class PlacesCacheService implements PlacesCacheRepo {

    @Autowired
    @Delegate
    private PlacesCacheRepo repo;

    public Mono<PlacesCache> cache(PlacesResult r, String input, String lang) {

        var cache = new PlacesCache();
        if (lang != null) {
            cache.setLang(lang.toLowerCase());
        }

        cache.setQuery(input.toLowerCase());
        cache.setResult(r);
        return save(cache);
    }

    public Mono<PlacesCache> findCache(String input, String lang) {
        return repo.findFirstByQueryAndLang(input.toLowerCase(), lang == null ? lang : lang.toLowerCase());
    }

}

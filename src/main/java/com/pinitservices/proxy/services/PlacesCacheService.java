/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.services;

import com.pinitservices.proxy.googleApiModel.PlacesResult;
import com.pinitservices.proxy.model.CacheHit;
import com.pinitservices.proxy.model.PlacesCache;
import com.pinitservices.proxy.repositories.PalacesCacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


/**
 *
 */
@Slf4j
@Service
public class PlacesCacheService extends CacheServiceBase<PlacesCache> {

    private final CacheHitService hitService;

    public PlacesCacheService(PalacesCacheRepository repository,
                              CacheHitService hitService,
                              @Value("${googleApiCache}") boolean enabled,
                              MongoTemplate mongoTemplate) {
        super(PlacesCache.class, repository, enabled, mongoTemplate);
        this.hitService = hitService;
    }


    public boolean cache(PlacesResult result, String query, String lang, String userId) {
        if (!enabled) {
            return false;
        }
        try {
            return switch (result.getStatus()) {
                case OK, ZERO_RESULTS -> {
                    PlacesCache cache = new PlacesCache(result, query.toLowerCase(), lang);
                    cache.setUserId(userId);
                    save(cache);
                    hitService.save(new CacheHit(cache.getId(), false, userId, "Places"));
                    yield true;
                }
                default -> false;

            };

        } catch (Exception ex) {
            log.error("Could not insert cache", ex);
        }
        return false;

    }

    public PlacesResult findCache(String query, String lang, String userId) {
        if (!enabled) {
            return null;
        }
        final PlacesCache cache = findOne(

                Query.query(
                        Criteria.where(PlacesCache.Fields.query).is(query.toLowerCase())
                                .and(PlacesCache.Fields.lang).is(lang)
                ));
        if (cache != null) {
            hitService.save(new CacheHit(cache.getId(), true, userId, "Places"));
            return cache.getResult();
        }
        return null;
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pinitservices.proxy.services;

import com.pinitservices.proxy.model.GeocodeResponse;
import com.pinitservices.proxy.model.GeocodeResult;
import com.pinitservices.proxy.model.cache.Cache;
import com.pinitservices.proxy.model.cache.GeocodeCache;
import com.pinitservices.proxy.repositories.GeocodeCacheRepo;
import lombok.experimental.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metric;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 *
 * @author Ramdane
 */
@Service
public class GeocodeCacheService implements GeocodeCacheRepo {

    private int nearDistance = 300;

    @Autowired
    @Delegate
    private GeocodeCacheRepo repo;

    @Autowired
    private ReactiveMongoTemplate template;

    public Mono<GeocodeCache> cache(GeocodeResponse response, String lang) {
        return save(new GeocodeCache(response, lang));
    }

    Mono<GeocodeCache> findCache(String placeId, String lang) {

        return template.findOne(new Query()
                .addCriteria(
                        Criteria.where(
                                String.format("%s.%s", GeocodeCache.Fields.result, GeocodeResult.Fields.placeId)
                        ).is(placeId).and(Cache.Fields.lang).is(lang)
                ),
                GeocodeCache.class);

    }

    Mono<GeocodeCache> findCache(double lat, double lng, String lang) {
        /**
         * @TODO later
         */

        NearQuery nearQuery = NearQuery.near(lng, lng);
        nearQuery.maxDistance(new Distance(nearDistance / 1000.0, Metrics.KILOMETERS));
        nearQuery.spherical(true);

        //return template.geoNear(nearQuery, entityClass)
        return template.findOne(new Query().addCriteria(
                Criteria.where(Cache.Fields.lang).is(lang)
                        .and(
                                String.format("%s.%s", GeocodeCache.Fields.result, GeocodeResult.Fields.center)
                        ).near(new Point(lng, lng))
        ),
                GeocodeCache.class);

    }
}

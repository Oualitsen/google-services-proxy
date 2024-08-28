/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.services;

import com.pinitservices.proxy.googleApiModel.GeocodeResponse;
import com.pinitservices.proxy.googleApiModel.GeocodeResult;
import com.pinitservices.proxy.googleApiModel.Geometry;
import com.pinitservices.proxy.model.CacheHit;
import com.pinitservices.proxy.model.GeocodeCache;
import com.pinitservices.proxy.model.MyCircle;
import com.pinitservices.proxy.repositories.GeocodeCacheRepository;
import com.pinitservices.proxy.utils.MongoDBUtils;
import jakarta.annotation.PostConstruct;
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
public class GeocodeCacheService extends CacheServiceBase<GeocodeCache> {


    private final CacheHitService hitService;


    public GeocodeCacheService(GeocodeCacheRepository repository,
                               CacheHitService hitService,
                               @Value("${googleApiCache}") boolean enabled,
                               MongoTemplate mongoTemplate
    ) {
        super(GeocodeCache.class, repository, enabled, mongoTemplate);
        this.hitService = hitService;
    }

    @PostConstruct
    public void init() {


    }

    public boolean cache(GeocodeResponse response, String userId) {
        if (!enabled) {
            return false;
        }
        return switch (response.getStatus()) {
            case OK, ZERO_RESULTS -> {

                final GeocodeCache cache = new GeocodeCache(response);
                save(cache);
                hitService.save(new CacheHit(cache.getId(), false, userId, "Geocode"));
                yield true;
            }
            default -> false;

        };

    }

    public GeocodeResponse findCache(String placeId, String userId) {
        if (!enabled) {
            return null;
        }
        final var query = Query.query(Criteria.where(MongoDBUtils.cat(GeocodeCache.Fields.response,
                GeocodeResponse.Fields.results, GeocodeResult.Fields.placeId)).is(placeId));
        final GeocodeCache cache = findOne(query);

        if (cache != null) {
            hitService.save(new CacheHit(cache.getId(), false, userId, "Geocode"));
            final GeocodeResponse response = cache.getResponse();
            if (response.getResults() != null) {
                response.setResults(response.getResults().stream().filter(e -> placeId.equals(e.getPlaceId())).toList());
            }
            return response;
        }
        return null;

    }

    public GeocodeResponse findCache(double lat, double lng, String userId) {
        log.info("######### lat = " + lat + ", lng = " + lng);
        if (!enabled) {
            return null;
        }

        final var query = Query.query(
                Criteria.where(MongoDBUtils.cat(GeocodeCache.Fields.result, GeocodeResult.Fields.geometry, Geometry.Fields.location))
                        .withinSphere(new MyCircle(lat, lng, 300))
        );


        final GeocodeCache findOne = findOne(query);

        if (findOne != null) {
            hitService.save(new CacheHit(findOne.getId(), false, userId, "Geocode"));
            return findOne.getResponse();
        }
        return null;

    }


}

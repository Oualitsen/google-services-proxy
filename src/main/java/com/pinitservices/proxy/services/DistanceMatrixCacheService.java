/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.services;

import com.pinitservices.proxy.googleApiModel.Coords;
import com.pinitservices.proxy.googleApiModel.DistanceMatrixResponse;
import com.pinitservices.proxy.model.CacheHit;
import com.pinitservices.proxy.model.DistanceMatrixCache;
import com.pinitservices.proxy.model.MyCircle;
import com.pinitservices.proxy.repositories.DistanceMatrixCacheRepository;
import com.pinitservices.proxy.utils.CoordsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 *
 */
@Slf4j
@Service
public class DistanceMatrixCacheService extends CacheServiceBase<DistanceMatrixCache> {

    private final int minDistance;

    private final int cacheTimeOut;

    private final CacheHitService hitService;

    public DistanceMatrixCacheService(DistanceMatrixCacheRepository repository,
                                      CacheHitService hitService,
                                      @Value("${cache.distance-matrix.min-distance}")
                                      int minDistance,
                                      @Value("${cache.distance-matrix.timeout}")
                                      int cacheTimeOut,
                                      @Value("${googleApiCache}") boolean enabled,
                                      MongoTemplate mongoTemplate
    ) {
        super(DistanceMatrixCache.class, repository, enabled, mongoTemplate);
        this.minDistance = minDistance;
        this.cacheTimeOut = cacheTimeOut;
        this.hitService = hitService;
    }


    public boolean cache(DistanceMatrixResponse response, Coords origins, Coords destinations, String when, boolean withTraffic, String userId) {
        if (!enabled) {
            return false;
        }
        long date = "now".equals(when) ? System.currentTimeMillis() : Long.parseLong(when);
        return cache(response, origins.getUriFormat(), destinations.getUriFormat(), date, withTraffic, userId);

    }

    public boolean cache(DistanceMatrixResponse response, String origins, String destinations, long when, boolean withTraffic, String userId) {

        if (!enabled) {
            return false;
        }
        return switch (response.getStatus()) {
            case OK, ZERO_RESULTS -> {
                DistanceMatrixCache cache = new DistanceMatrixCache();
                cache.setResponse(response);
                cache.setOrigins(origins);
                cache.setDestinations(destinations);
                cache.setTime(when);
                cache.setWithoutTraffic(withTraffic);
                cache.setUserId(userId);
                save(cache);
                hitService.save(new CacheHit(cache.getId(), false, userId, "distanceMatrix"));
                yield true;
            }
            default -> false;
        };


    }

    public DistanceMatrixResponse findCache(Coords src, Coords dest, boolean without, String userId) {
        if (!enabled) {
            return null;
        }

        double[] origs = {src.lat(), src.lng()};
        double[] dests = {dest.lat(), dest.lng()};
        final var builder = Criteria.where(DistanceMatrixCache.Fields.withoutTraffic).is(without)
                .and(DistanceMatrixCache.Fields.origin)
                .withinSphere(new MyCircle(origs[0], origs[1], minDistance));


        if (without) {
            builder.and(DistanceMatrixCache.Fields.time).gte(System.currentTimeMillis() - cacheTimeOut * 1000L);
        }

        final List<DistanceMatrixCache> list1 = find(Query.query(builder));

        if (CollectionUtils.isEmpty(list1)) {
            return null;
        }


        final var builder2 = Criteria.where(DistanceMatrixCache.Fields.withoutTraffic).is(without)
                .and(DistanceMatrixCache.Fields.destination)
                .withinSphere(new MyCircle(dests[0], dests[1], minDistance));


        if (without) {
            builder2.and(DistanceMatrixCache.Fields.time).gte(System.currentTimeMillis() - cacheTimeOut * 1000L);
        }


        final List<DistanceMatrixCache> list2 = find(Query.query(builder2));

        if (list2 == null || list2.isEmpty()) {
            return null;
        }

        double distanceSum = Double.MAX_VALUE;

        DistanceMatrixCache cache = null;

        for (DistanceMatrixCache r : list2) {
            double d = CoordsUtils.distance(src, new Coords(r.getOrigin().getCoordinates()[0], r.getOrigin().getCoordinates()[1]))
                    + CoordsUtils.distance(dest, new Coords(r.getDestination().getCoordinates()[0], r.getDestination().getCoordinates()[1]));
            if (d < distanceSum) {
                cache = r;
                distanceSum = d;
            }
        }

        if (cache != null) {
            hitService.save(new CacheHit(cache.getId(), true, userId, "distanceMatrix"));
            return cache.getResponse();
        }
        return null;
    }


}

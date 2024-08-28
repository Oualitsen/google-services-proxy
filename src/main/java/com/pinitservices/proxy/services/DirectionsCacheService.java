/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.services;

import com.pinitservices.proxy.googleApiModel.Coords;
import com.pinitservices.proxy.googleApiModel.DirectionResult;
import com.pinitservices.proxy.model.CacheHit;
import com.pinitservices.proxy.model.DirectionsCache;
import com.pinitservices.proxy.model.MyCircle;
import com.pinitservices.proxy.model.geojson.GeoPoint;
import com.pinitservices.proxy.repositories.DirectionsCacheRepository;
import com.pinitservices.proxy.utils.CoordsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Slf4j
@Service
public class DirectionsCacheService extends CacheServiceBase<DirectionsCache> {

    private final int minDistance;
    private final CacheHitService hitService;


    public DirectionsCacheService(DirectionsCacheRepository repository,
                                  CacheHitService cacheHitService,
                                  @Value("${cache.directions.min-distance}") int minDistance,
                                  @Value("${googleApiCache}") boolean enabled,
                                  MongoTemplate mongoTemplate) {
        super(DirectionsCache.class, repository, enabled, mongoTemplate);
        this.minDistance = minDistance;
        this.hitService = cacheHitService;
    }


    public boolean cache(DirectionResult result, Coords orig, Coords dest, boolean withTrafficInfo, String lang, long departureTime, String userId) {
        if (!enabled) {
            return false;
        }

        switch (result.getStatus()) {
            case OK, ZERO_RESULTS -> {
                final DirectionsCache cache = new DirectionsCache(result, withTrafficInfo, lang, departureTime);
                cache.setUserId(userId);
                cache.setOrigin(new GeoPoint(orig.lat(), orig.lng()));
                cache.setDestination(new GeoPoint(dest.lat(), dest.lng()));
                save(cache);
                hitService.save(new CacheHit(cache.getId(), false, userId, "directions"));
                return true;
            }
        }
        return false;

    }

    public DirectionResult findCache(Coords origin, Coords destination, String lang, boolean withTraffic, String userId) {
        if (!enabled) {
            return null;
        }
        final var builder = Criteria.where(DirectionsCache.Fields.origin)
                .withinSphere(new MyCircle(origin.lat(), origin.lng(), minDistance))
                .and(DirectionsCache.Fields.lang).is(lang)
                .and(DirectionsCache.Fields.withTrafficInfo).is(withTraffic);

        if (withTraffic) {
            builder.and(DirectionsCache.Fields.departureTime).gte(System.currentTimeMillis() - 30 * 60 * 1000);
        }

        final List<DirectionsCache> list1 = find(Query.query(builder));

        if (list1.isEmpty()) {
            return null;
        }


        final var criteria2 = Criteria.where(DirectionsCache.Fields.destination)
                .withinSphere(new MyCircle(destination.lat(), destination.lng(), minDistance))
                .and(DirectionsCache.Fields.lang).is(lang)
                .and(DirectionsCache.Fields.withTrafficInfo).is(withTraffic);


        final List<DirectionsCache> list2 = find(Query.query(criteria2));

        if (list2 == null || list2.isEmpty()) {
            log.info("DIRECTIONS ###2");
            return null;
        }
        double distanceSum = Double.MAX_VALUE;

        DirectionsCache cache = null;

        for (DirectionsCache r : list2) {
            double d = CoordsUtils.distance(origin, new Coords(r.getOrigin().getCoordinates()[0], r.getOrigin().getCoordinates()[1]))
                    + CoordsUtils.distance(destination, new Coords(r.getDestination().getCoordinates()[0], r.getDestination().getCoordinates()[1]));
            if (d < distanceSum) {
                cache = r;
                distanceSum = d;
            }
        }
        if (cache != null) {
            hitService.save(new CacheHit(cache.getId(), true, userId, "directions"));
            return cache.getResult();
        }
        return null;

    }


}

package com.pinitservices.proxy.services;

import com.pinitservices.proxy.model.DirectionResult;
import com.pinitservices.proxy.model.DirectionResultStatus;
import com.pinitservices.proxy.model.cache.DirectionsCache;
import com.pinitservices.proxy.model.geojson.GeoPoint;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.pinitservices.proxy.services.repositories.DirectionsCacheRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

/**
 *
 * @author Ramdane
 */
@Log
@Service
public class DirectionsCacheService extends CacheService<DirectionsCache> {

    @Value("${maxDistanceMatrixDistance}")
    private double distance;

    @Delegate
    private final DirectionsCacheRepository repository;

    public DirectionsCacheService(DirectionsCacheRepository repository, ReactiveMongoTemplate template) {
        super(repository, template);
        this.repository = repository;
    }

    public Mono<DirectionsCache> cahce(DirectionResult dr, GeoPoint origin,
            GeoPoint destination, long time, String lang, boolean traffic) {

        return Mono.just(cacheEnabled).filter(c -> c).map(c -> dr)
                .filter(r -> r.getStatus() == DirectionResultStatus.OK && cacheEnabled)
                .map(e -> from(e, origin, destination, time, lang, traffic))
                .flatMap(this::save)
                .switchIfEmpty(Mono.defer(() -> Mono.just(from(dr, origin, destination, time, lang, traffic))));

    }

    public static DirectionsCache from(DirectionResult dr, GeoPoint origin,
            GeoPoint destination, long time, String lang, boolean traffic) {
        DirectionsCache dc = new DirectionsCache();
        dc.setLang(lang);
        dc.setDepartureTime(time);
        dc.setDestination(destination);
        dc.setOrigin(origin);
        dc.setWithTrafficInfo(traffic);
        dc.setResult(dr);
        return dc;
    }

    public Mono<DirectionsCache> findCache(GeoPoint origin, GeoPoint destination, String lang, boolean trafic, long when) {

        /**
         * @TODO apply to when
         */
        double srcLat = origin.getLat();
        double srcLng = origin.getLng();
        double destLat = destination.getLat();
        double destLng = destination.getLng();
        var origins = repository.findCacheOrigin(lang, srcLat, srcLng, distance, trafic);
        var destinations = repository.findCacheDestination(lang, destLat, destLng, distance, trafic);
        return Mono.just(cacheEnabled).filter(c -> c).flatMapMany(c -> Flux.merge(origins, destinations))
                .groupBy(it -> it)
                .flatMap(group -> Mono.zip(Mono.just(group.key()), group.count()))
                .filter(e -> e.getT2() > 1)
                .map(e -> e.getT1())
                .next();

    }

}

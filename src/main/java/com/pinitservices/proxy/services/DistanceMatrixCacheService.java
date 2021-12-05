package com.pinitservices.proxy.services;

import com.pinitservices.proxy.model.DistanceMatrixResponse;
import com.pinitservices.proxy.model.ResponseStatus;
import com.pinitservices.proxy.model.cache.DistanceMatrixCache;
import com.pinitservices.proxy.model.geojson.GeoPoint;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.pinitservices.proxy.services.repositories.DistanceMatrixCacheRepository;
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
public class DistanceMatrixCacheService extends CacheService<DistanceMatrixCache> {

    @Value("${maxDistanceMatrixDistance}")
    private double distance;

    @Delegate
    private final DistanceMatrixCacheRepository repository;

    public DistanceMatrixCacheService(DistanceMatrixCacheRepository repository, ReactiveMongoTemplate template) {
        super(repository, template);
        this.repository = repository;
    }

    public Mono<DistanceMatrixCache> cache(DistanceMatrixResponse response, GeoPoint origin, GeoPoint destination, String lang, long time, boolean traffic) {

        return Mono.just(cacheEnabled).filter(c -> c).map(c -> response).filter(r -> r.getStatus() == ResponseStatus.OK)
                .map(r -> form(response, origin, destination, lang, time, traffic)).flatMap(this::save).switchIfEmpty(Mono.defer(()
                -> Mono.just(form(response, origin, destination, lang, time, traffic))
        ));

    }

    public static DistanceMatrixCache form(DistanceMatrixResponse response, GeoPoint origin, GeoPoint destination, String lang, long time, boolean traffic) {
        var cache = new DistanceMatrixCache();
        cache.setLang(lang);
        cache.setDestination(destination);
        cache.setOrigin(origin);
        cache.setResponse(response);
        cache.setTime(time);
        cache.setWithTrafficInfo(traffic);
        return cache;

    }

    public Mono<DistanceMatrixCache> findCache(GeoPoint origin, GeoPoint destination, String lang, boolean trafic, long when) {
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
                .map(e -> {

                    log.info("id = " + e.getT1().getId() + " count = " + e.getT2());
                    return e;

                })
                .filter(e -> e.getT2() > 1)
                .map(e -> e.getT1())
                .next();

    }

}

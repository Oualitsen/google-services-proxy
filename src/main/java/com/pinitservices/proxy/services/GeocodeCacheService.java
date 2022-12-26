package com.pinitservices.proxy.services;

import com.pinitservices.proxy.model.GeocodeResponse;
import com.pinitservices.proxy.model.GeocodeResult;
import com.pinitservices.proxy.model.ResponseStatus;
import com.pinitservices.proxy.model.cache.Cache;
import com.pinitservices.proxy.model.cache.GeocodeCache;
import lombok.experimental.Delegate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.pinitservices.proxy.services.repositories.GeocodeCacheRepository;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author Ramdane
 */
@Service
public class GeocodeCacheService extends CacheService<GeocodeCache> {

    @Value("${maxDistance}")
    private int maxDistance;

    @Delegate
    private final GeocodeCacheRepository repository;

    public GeocodeCacheService(GeocodeCacheRepository repository, ReactiveMongoTemplate template) {
        super(repository, template);
        this.repository = repository;

    }

    public Mono<GeocodeCache> cache(GeocodeResponse response, String lang) {

        return Mono.just(cacheEnabled).filter(c -> c).map(c -> response)
                .filter(cache -> cache.getStatus() == ResponseStatus.OK)
                .map(r -> new GeocodeCache(r, lang))
                .flatMap(this::save)
                .switchIfEmpty(Mono.defer(() -> Mono.just(new GeocodeCache(response, lang))));

    }

    public Mono<GeocodeCache> findCache(String placeId, String lang) {

        return template.findOne(new Query(Criteria.where(
                String.format("%s.%s", GeocodeCache.Fields.result, GeocodeResult.Fields.placeId)
        ).is(placeId).and(Cache.Fields.lang).is(lang)),
                GeocodeCache.class);

    }

    /**
     * used for reverse geocode
     *
     * @param lat
     * @param lng
     * @param lang
     * @return
     */
    public Mono<GeocodeCache> findCache(double lat, double lng, String lang) {

        return Mono.just(cacheEnabled).filter(c -> c).flatMapMany(c -> findCache(lang, lat, lng, maxDistance)).next();
    }
}

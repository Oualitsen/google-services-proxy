package com.pinitservices.proxy.services.repositories;

import com.pinitservices.proxy.model.cache.PlacesCache;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PlacesCacheRepository extends CacheRepository<PlacesCache> {

    Mono<PlacesCache> findFirstByQueryAndLang(String query, String lang);

}

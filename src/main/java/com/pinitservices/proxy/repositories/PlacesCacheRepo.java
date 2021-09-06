package com.pinitservices.proxy.repositories;

import com.pinitservices.proxy.model.cache.PlacesCache;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PlacesCacheRepo extends ReactiveMongoRepository<PlacesCache, String> {

    Mono<PlacesCache> findFirstByQueryAndLang(String query, String lang);

}

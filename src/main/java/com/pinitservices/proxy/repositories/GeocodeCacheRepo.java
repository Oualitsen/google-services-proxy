package com.pinitservices.proxy.repositories;

import com.pinitservices.proxy.model.cache.GeocodeCache;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeocodeCacheRepo extends ReactiveMongoRepository<GeocodeCache, String> {

}

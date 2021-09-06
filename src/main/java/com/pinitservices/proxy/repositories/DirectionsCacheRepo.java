package com.pinitservices.proxy.repositories;

import com.pinitservices.proxy.model.cache.DirectionsCache;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectionsCacheRepo extends ReactiveMongoRepository<DirectionsCache, String> {

}

package com.pinitservices.proxy.repositories;

import com.pinitservices.proxy.model.cache.DistanceMatrixCache;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistanceMatrixCacheRepo extends ReactiveMongoRepository<DistanceMatrixCache, String> {

}

package com.pinitservices.proxy.services.repositories;

import com.pinitservices.proxy.model.cache.Cache;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CacheRepository<T extends Cache> extends ReactiveMongoRepository<T, String> {

}

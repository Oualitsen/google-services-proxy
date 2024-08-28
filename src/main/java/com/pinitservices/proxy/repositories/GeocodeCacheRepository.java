package com.pinitservices.proxy.repositories;

import com.pinitservices.proxy.model.GeocodeCache;
import org.springframework.stereotype.Repository;

@Repository
public interface GeocodeCacheRepository extends CacheRepository<GeocodeCache> {


}

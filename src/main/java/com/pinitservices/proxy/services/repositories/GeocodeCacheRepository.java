package com.pinitservices.proxy.services.repositories;

import com.pinitservices.proxy.model.cache.GeocodeCache;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface GeocodeCacheRepository extends CacheRepository<GeocodeCache> {

    @Query("""
           {
                "lang": ?0,
                "result.center": {
                    $near : {
                        $geometry: {
                            type: "Point",
                            "coordinates": [?2, ?1]
                        },
                        $maxDistance: ?3
                    }
                }
           
           }
           """)
    public Flux<GeocodeCache> findCache(String lang, double lat, double lng, double maxDistance);

}

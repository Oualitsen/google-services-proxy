package com.pinitservices.proxy.services.repositories;

import com.pinitservices.proxy.model.cache.DirectionsCache;
import com.pinitservices.proxy.model.cache.DistanceMatrixCache;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface DistanceMatrixCacheRepository extends CacheRepository<DistanceMatrixCache> {

    @Query("""
           {
              "lang":?0,
              "withTrafficInfo":?4,
              "origin":{
                    "$near":{
                       "$geometry":{
                          "type":"Point",
                          "coordinates":[
                             ?2,
                             ?1
                          ]
                       },
                       "$maxDistance":?3
                    }
                 }
           }
           """)

    Flux<DistanceMatrixCache> findCacheOrigin(String lang, double lat, double lng, double distance, boolean traffic);

    @Query("""
           {
              "lang":?0,
              "withTrafficInfo":?4,
              "destination":{
                        "$near":{
                           "$geometry":{
                              "type":"Point",
                              "coordinates":[
                                 ?2,
                                 ?1
                              ]
                           },
                           "$maxDistance":?3
                        }
                    }
           }
           """)
    Flux<DistanceMatrixCache> findCacheDestination(String lang, double lat, double lng, double distance, boolean traffic);

}

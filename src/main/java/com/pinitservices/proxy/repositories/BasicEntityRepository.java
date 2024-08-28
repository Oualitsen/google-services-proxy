package com.pinitservices.proxy.repositories;

import com.pinitservices.proxy.BasicEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BasicEntityRepository<T extends BasicEntity> extends MongoRepository<T, String> {

    @Query("""
            {"_id": ?0, "lastUpdate" : {$gt: ?1}}
            """)
    T findIfNewer(String id, long date);

    @Query(value = """
                {"creationDate": {$gte: ?0}}
            """, count = true)
    long countByCreationDate(long creationDate);
}

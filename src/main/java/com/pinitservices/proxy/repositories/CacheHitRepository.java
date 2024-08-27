package com.pinitservices.proxy.repositories;

import com.pinitservices.proxy.model.CacheHit;
import org.springframework.stereotype.Repository;

@Repository
public interface CacheHitRepository extends BasicEntityRepository<CacheHit> {
}

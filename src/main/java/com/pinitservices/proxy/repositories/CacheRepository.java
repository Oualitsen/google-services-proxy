package com.pinitservices.proxy.repositories;

import com.pinitservices.proxy.model.Cache;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CacheRepository<T extends Cache> extends BasicEntityRepository<T> {
}

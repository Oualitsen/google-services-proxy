/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.services;

import com.pinitservices.proxy.model.Cache;
import com.pinitservices.proxy.repositories.CacheRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @param <T>
 */
public abstract class CacheServiceBase<T extends Cache> extends BasicEntityService<T> {

    protected final boolean enabled;

    public CacheServiceBase(Class<T> _class,
                            CacheRepository<T> repository,
                            @Value("${googleApiCache}") boolean enabled,
                            MongoTemplate template) {

        super(_class, template, repository);
        this.enabled = enabled;
    }
}

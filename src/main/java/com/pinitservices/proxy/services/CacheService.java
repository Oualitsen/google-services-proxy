/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pinitservices.proxy.services;

import com.pinitservices.proxy.model.cache.Cache;
import com.pinitservices.proxy.services.repositories.CacheRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

/**
 *
 * @author Ramdane
 * @param <T>
 */
@RequiredArgsConstructor
public class CacheService<T extends Cache> implements CacheRepository<T> {

    @Value("${cacheEnabled}")
    protected boolean cacheEnabled;

    @Delegate
    private final CacheRepository<T> repository;

    protected final ReactiveMongoTemplate template;

}

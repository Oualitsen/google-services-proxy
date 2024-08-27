/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.services;

import com.pinitservices.proxy.model.CacheHit;
import com.pinitservices.proxy.repositories.CacheHitRepository;
import com.pinitservices.proxy.services.BasicEntityService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class CacheHitService extends BasicEntityService<CacheHit> {

    public CacheHitService(CacheHitRepository repository, MongoTemplate template) {
        super(CacheHit.class, template, repository);
    }

}

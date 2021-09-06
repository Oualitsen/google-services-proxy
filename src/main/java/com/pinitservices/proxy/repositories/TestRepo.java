package com.pinitservices.proxy.repositories;

import com.pinitservices.proxy.model.BasicEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepo extends ReactiveMongoRepository<BasicEntity, String> {

}

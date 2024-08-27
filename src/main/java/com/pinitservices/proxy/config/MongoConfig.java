package com.pinitservices.proxy.config;

import java.util.Collection;
import java.util.Collections;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MongoConfig  {

    

    
    private final MongoClient client;

    @Value("${spring.data.mongodb.database}")
    private String dbName;

    @Bean
    public MyMongoTemplate mongoTemplate() {
        return new MyMongoTemplate(client, dbName);
    }

    
}

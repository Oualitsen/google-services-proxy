package com.pinitservices.proxy.config;

import java.util.Collection;
import java.util.Collections;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;

import lombok.extern.java.Log;

@Log
// @Configuration
public class MongoConfig {

    @Value("${mongo.url}")
    private String url;
    @Value("${mongo.dbname}")
    private String dbName;

    public MongoClient mongoClient() {
        log.info("connection string " + url + dbName);
        ConnectionString connectionString = new ConnectionString(url + dbName);
        var mongoClientSettings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
        return MongoClients.create(mongoClientSettings);
    }

}

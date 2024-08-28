package com.pinitservices.proxy.config;

import com.mongodb.client.MongoClient;
import lombok.extern.java.Log;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UpdateDefinition;

@Log
public class MyMongoTemplate extends MongoTemplate {

    public static final FindAndModifyOptions DEFAULT_OPTIONS = FindAndModifyOptions.options().returnNew(true);

    public MyMongoTemplate(MongoClient mongoClient, String databaseName) {
        super(mongoClient, databaseName);
    }

    @Override
    public <T> T findAndModify(Query query, UpdateDefinition update, Class<T> entityClass) {
        return super.findAndModify(query, update, DEFAULT_OPTIONS, entityClass);
    }

    @Override
    public <T> T findAndModify(Query query, UpdateDefinition update, Class<T> entityClass,
                               String collectionName) {
        return super.findAndModify(query, update, DEFAULT_OPTIONS, entityClass, collectionName);
    }


}

package com.pinitservices.proxy.services;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.pinitservices.proxy.BasicEntity;
import com.pinitservices.proxy.repositories.BasicEntityRepository;
import com.pinitservices.proxy.utils.MongoDBUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class BasicEntityService<T extends BasicEntity> {
    protected final Class<T> _class;
    protected final MongoTemplate template;
    private final BasicEntityRepository<T> repository;


    public static Update addLastUpdate(Update update) {
        update.set(BasicEntity.Fields.lastUpdate, System.currentTimeMillis());
        return update;
    }

    protected T findIfNewer(final String id, final long date) {
        return repository.findIfNewer(id, date);
    }

    public <S extends T> List<S> saveAll(final Iterable<S> entities) {
        return repository.saveAll(entities);
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public List<T> findAll(final Sort sort) {
        return repository.findAll(sort);
    }

    public Page<T> findAll(final Pageable pageable) {
        return repository.findAll(pageable);
    }

    public T save(final T entity) {
        return repository.save(entity);
    }

    public void deleteById(final String s) {
        if (s != null) {
            repository.deleteById(s);
        }
    }

    public DeleteResult remove(final Query query) {
        return this.template.remove(query, this._class);
    }

    @Nullable
    public T findOne(final Query query) {
        return template.findOne(query, this._class);
    }

    public boolean exists(final Query query) {
        return this.template.exists(query, this._class);
    }

    public List<T> find(final Query query) {
        return this.template.find(query, this._class);
    }

    public Optional<T> findById(final String id) {
        return Optional.ofNullable(findOne(MongoDBUtils.id(id)));
    }

    @Nullable
    public T findAndModify(final Query query, final Update update, @Nullable final FindAndModifyOptions options) {
        var r = this.template.findAndModify(query, addLastUpdate(update), options, this._class);
        return r;
    }

    @Nullable
    public T findAndModify(final String entityId, final Update update, final FindAndModifyOptions options) {
        return findAndModify(MongoDBUtils.id(entityId), update, options);
    }

    @Nullable
    public T findAndModify(final String entityId, final Update update) {
        return findAndModify(entityId, update, null);
    }

    public T findAndModify(final Query query, final Update update) {
        return findAndModify(query, update, null);
    }

    public T findOne(final String entityId) {
        return this.findOne(MongoDBUtils.id(entityId));
    }

    public long count(final Query query) {
        return this.template.count(query, this._class);
    }

    public long count() {
        return repository.count();
    }

    public List<T> findAll(final Iterable<String> ids) {
        final List<T> list = new ArrayList<>();

        final var iterable = repository.findAllById(ids);
        for (final T t : iterable) {
            list.add(t);
        }
        return list;
    }

    public T updateOneField(final String entityId, final String field, final Object value) {
        return updateOneField(MongoDBUtils.id(entityId), field, value);
    }

    public T updateOneField(final Query query, final String field, final Object value) {
        return findAndModify(query, MongoDBUtils.set(field, value));
    }

    @Nullable
    public T findAndRemove(final Query query) {
        return this.template.findAndRemove(query, this._class);
    }

    @Nullable
    public T findAndRemove(final String entityId) {
        return this.template.findAndRemove(MongoDBUtils.id(entityId), this._class);
    }

    public UpdateResult updateMulti(final Query query, final Update update) {
        return template.updateMulti(query, addLastUpdate(update), this._class);
    }

    public long countByCreationDate(final long creationDate) {
        return repository.countByCreationDate(creationDate);
    }

    public T unsetIf(String entityId, String field, String ifValue) {
        return findAndModify(Query.query(MongoDBUtils.idCriteria(entityId).and(field).is(ifValue)), new Update().unset(field));
    }

    public UpdateResult updateFirst(Query query, Update update) {
        return template.updateFirst(query, addLastUpdate(update), _class);
    }

    public UpdateResult updateFirst(String entityId, Update update) {
        return updateFirst(MongoDBUtils.id(entityId), update);
    }

    public UpdateResult upsert(Query query, Update update) {
        return template.upsert(query, addLastUpdate(update), _class);
    }


}

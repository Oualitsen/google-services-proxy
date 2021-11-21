package com.pinitservices.proxy.services;

import com.pinitservices.proxy.model.PlacesResult;
import com.pinitservices.proxy.model.ResponseStatus;
import com.pinitservices.proxy.model.cache.PlacesCache;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.pinitservices.proxy.services.repositories.PlacesCacheRepository;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

/**
 *
 * @author Ramdane
 */
@Service
public class PlacesCacheService extends CacheService<PlacesCache> {

    @Delegate
    private final PlacesCacheRepository repository;

    public PlacesCacheService(PlacesCacheRepository repository, ReactiveMongoTemplate template) {
        super(repository, template);
        this.repository = repository;
    }

    public Mono<PlacesCache> cache(PlacesResult response, String input, String lang) {

        return Mono.just(cacheEnabled)
                .filter(c -> c)
                .map(c -> response)
                .filter(r -> r.getStatus() == ResponseStatus.OK)
                .map(r -> from(response, input, lang)).flatMap(this::save).switchIfEmpty(Mono.defer(() -> Mono.just(from(response, input, lang))));

    }

    public static PlacesCache from(PlacesResult response, String input, String lang) {
        var cache = new PlacesCache();

        cache.setQuery(input.toLowerCase());
        cache.setResult(response);
        return cache;
    }

    public Mono<PlacesCache> findCache(String input, String lang) {
        return Mono.just(cacheEnabled).filter(c -> c).flatMap(c -> repository.findFirstByQueryAndLang(input.toLowerCase(), lang.toLowerCase()));
    }

}

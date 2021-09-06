package com.pinitservices.proxy.controllers;

import com.pinitservices.proxy.model.BasicEntity;
import com.pinitservices.proxy.remote.RemoteApiService;
import com.pinitservices.proxy.repositories.PlacesCacheRepo;
import com.pinitservices.proxy.repositories.TestRepo;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private Logger logger;

    @Autowired
    private WebClient webClient;

    @Autowired
    private RemoteApiService service;

    @Autowired
    private PlacesCacheRepo placesCacheRepo;

    @Autowired
    private TestRepo testRepo;

    @GetMapping()
    public Mono<String> test(@RequestParam(required = false) String key) {
        logger.info("testing ########@@@ " + key);
        return Mono.just("{'azul':'fellawen'}");
    }

    @GetMapping("{input}")
    public Object test2(@PathVariable String input) {

        if (true) {
            logger.info("testing ");
            BasicEntity basicEntity = new BasicEntity();
            new Thread(() -> {
                logger.info("Blocking on new thread");
                var be = testRepo.save(basicEntity).publishOn(Schedulers.boundedElastic()).block();
                logger.info("be = " + be.getId());

            }).start();

        }

        return 1;
    }

}

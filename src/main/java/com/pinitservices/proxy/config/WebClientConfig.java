package com.pinitservices.proxy.config;

import java.net.URI;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    @Autowired
    private Logger logger;

    /**
     * Testing
     */
    private static final String key = "AIzaSyAHVFlX0bnarFsf6YvyzyRDwlyR6yHjZgQ";

    @Bean
    public WebClient webClient() {
        logger.info("Hello world!!!");
        return WebClient.builder().baseUrl("https://maps.googleapis.com/maps/api/")
                //.baseUrl("http://localhost:8080")
                .filter(this::filter).build();
    }

    private Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction fn) {
        final String url = request.url().toString();

        final String newUrl;

        if (url.contains("?")) {
            newUrl = String.format("%s&key=%s", url, key);
        } else {
            newUrl = String.format("%s?key=%s", url, key);
        }

        return fn.exchange(ClientRequest.from(request)
                .url(URI.create(newUrl))
                .build());
    }

}

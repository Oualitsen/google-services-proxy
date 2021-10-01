package com.pinitservices.proxy.config;

import java.net.URI;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Configuration
@Log
public class WebClientConfig {

    /**
     * Testing
     */
    private static final String key = "AIzaSyAHVFlX0bnarFsf6YvyzyRDwlyR6yHjZgQ";

    @Bean
    public WebClient webClient() {
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinitservices.proxy.services.GoogleApiService;

import lombok.SneakyThrows;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * @author Ramdane
 */
@Configuration
public class GoogleApiServiceConfig {

    @Bean
    public GoogleApiService googleApiService(WebClient.Builder builder) {
        var webCLient = builder.baseUrl("https://maps.googleapis.com/maps/api/").build();
        var adapter = WebClientAdapter.create(webCLient);
        return HttpServiceProxyFactory.builderFor(adapter)
                .build()
                .createClient(GoogleApiService.class);
    }

}

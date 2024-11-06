package com.kt.dpla.support.webclient.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class ApiService {
    private final WebClient webClient;

    public <T> T getApiMono(String uri, Map<String, String> params, Class<T> clazz) {
        Mono<T> ret = webClient.get()
                            .uri(uriBuiler -> {
                                uriBuiler.path(uri);
                                params.forEach((k, v) -> {
                                    uriBuiler.queryParam(k, v);
                                });
                                return uriBuiler.build();
                            })
                            .retrieve()
                            .bodyToMono(clazz);
        return ret.block();
    }

    public <T> List<T> getApiFlux(String uri, Map<String, String> params, Class<T> clazz) {
        Flux<T> ret = webClient.get()
                            .uri(uriBuiler -> {
                                uriBuiler.path(uri);
                                params.forEach((k, v) -> {
                                    uriBuiler.queryParam(k, v);
                                });
                                return uriBuiler.build();
                            })
                            .retrieve()
                            .bodyToFlux(clazz);

        return ret.collectList().block();
    }

    public <T> T postApiMono(String uri, Map<String, String> params, Class<T> clazz) {
        Mono<T> ret = webClient.post()
                            .uri(uri)
                            .contentType(MediaType.APPLICATION_JSON).bodyValue(params)
                            .retrieve()
                .bodyToMono(clazz);

        return ret.block();
    }

    public <T> List<T> postApiFlux(String uri, Map<String, String> params, Class<T> clazz) {
        Flux<T> ret = webClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON).bodyValue(params)
                .retrieve()
                .bodyToFlux(clazz);

        return ret.collectList().block();
    }

}

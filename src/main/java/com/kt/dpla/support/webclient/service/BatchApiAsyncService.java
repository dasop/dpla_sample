package com.kt.dpla.support.webclient.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Lazy
@Service
public class BatchApiAsyncService {

    private WebClient webClient;

    @Autowired
    public BatchApiAsyncService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8088").build();
    }

    public List<String> batchNameSelectList() {
        Mono<List> ret = webClient.get()
                            .uri(uriBuiler -> {
                    uriBuiler.path("/schedule/online/jobName");
                                    return uriBuiler.build();
                                })
                            .retrieve()
                            .bodyToMono(List.class);
        ret.subscribe(list -> log.debug("list :: {}", list));

        return ret.block();
    }
}
package com.kt.dpla.support.rest.service;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BatchApiSyncService {

    private final RestTemplate restTemplate;

    private HttpHeaders getHeader() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        return header;
    }

    public List<String> batchNameSelectList(){
        URI uri = URI.create("http://localhost:8088/schedule/online/jobName");

        ResponseEntity<List> result = restTemplate.getForEntity(uri, List.class);

        return result.getBody();
    }
}

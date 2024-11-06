package com.kt.dpla.support.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.kt.dpla.support.util.JsonUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class RestApiService {

    private final RestTemplate restTemplate;

    private HttpHeaders getHeader(Map<String, Object> h_params) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        if (h_params != null) {
            for (Map.Entry<String, Object> entry : h_params.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof List && ((List) value).get(0) instanceof String)
                    header.put(key, (List<String>) value);
                else {
                    List<String> header_value = new ArrayList<String>();
                    header_value.add(String.valueOf(value));
                    header.put(key, header_value);
                }
            }
        }
        return header;
    }

    public Map<String, Object> get(String uri, Map<String, Object> param) {
        return get(uri, param, getHeader(null));
    }

    public Map<String, Object> get(String uri, Map<String, Object> param, Map<String, Object> h_params) {
        return get(uri, param, getHeader(h_params));
    }

    public Map<String, Object> get(String uri, Map<String, Object> param, HttpHeaders header) {
        String json = JsonUtil.toJson(param);

        HttpEntity<String> request = new HttpEntity<String>(json, header);

        ResponseEntity<Map> entity = restTemplate.exchange(uri, HttpMethod.GET, request, Map.class);

        return entity.getBody();
    }

    public Map<String, Object> post(String uri, Map<String, Object> param) {
        return post(uri, param, getHeader(null));
    }

    public Map<String, Object> post(String uri, Map<String, Object> param, Map<String, Object> h_params) {
        return post(uri, param, getHeader(h_params));
    }

    public Map<String, Object> post(String uri, Map<String, Object> param, HttpHeaders header) {
        String json = JsonUtil.toJson(param);

        HttpEntity<String> request = new HttpEntity<String>(json, header);

        log.info("[post]uri    : " + uri);
        log.info("[post]header : " + header);
        log.info("[post]body   : " + json);
        Map<String, Object> retMap = restTemplate.postForObject(uri, request, Map.class);

        return retMap;
    }

}

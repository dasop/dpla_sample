package com.kt.dpla.support.rest;

import java.nio.charset.Charset;
import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.DefaultUriBuilderFactory.EncodingMode;

import com.kt.dpla.support.rest.support.ApiRestTemplateErrorHandler;

@Configuration
public class RestTemplateConfig {

    @Bean(name = "restTemplate")
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {

        return restTemplateBuilder.requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
                .setConnectTimeout(Duration.ofMillis(60000))
                .setReadTimeout(Duration.ofMillis(60000))
                .additionalMessageConverters(new StringHttpMessageConverter(Charset.forName("UTF-8")))
                .errorHandler(new ApiRestTemplateErrorHandler())
                .build();
    }

    @Bean(name = "restTemplateMyApi")
    public RestTemplate restTemplateMyApi(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
                .setConnectTimeout(Duration.ofMillis(60000))
                .setReadTimeout(Duration.ofMillis(60000))
                .additionalMessageConverters(new StringHttpMessageConverter(Charset.forName("UTF-8")))
                .errorHandler(new ApiRestTemplateErrorHandler())
                .build();
    }

    @Bean(name = "restTemplateWithoutUrlEncoding")
    public RestTemplate restTemplateWithoutUrlEncoding(RestTemplateBuilder restTemplateBuilder) {
        DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory();
        defaultUriBuilderFactory.setEncodingMode(EncodingMode.NONE);

        RestTemplate restTemplate = restTemplateBuilder.requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
                .setConnectTimeout(Duration.ofMillis(5000))
                .setReadTimeout(Duration.ofMillis(5000))
                .additionalMessageConverters(new StringHttpMessageConverter(Charset.forName("UTF-8")))
                .build();

        restTemplate.setUriTemplateHandler(defaultUriBuilderFactory);
        return restTemplate;
    }
}

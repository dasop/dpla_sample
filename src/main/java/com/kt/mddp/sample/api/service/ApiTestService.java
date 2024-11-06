package com.kt.mddp.sample.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kt.dpla.support.masking.MaskingTarget;
import com.kt.dpla.support.rest.service.BatchApiSyncService;
import com.kt.dpla.support.webclient.service.BatchApiAsyncService;
import com.kt.mddp.sample.api.domain.Test;
import com.kt.mddp.sample.api.mapper.ApiTestMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ApiTestService {
    private final BatchApiAsyncService batchApiAsyncService;
    private final BatchApiSyncService batchApiSyncService;

    private final ApiTestMapper apiTestMapper;

    @MaskingTarget
    public List<String> test() {

        Test test = Test.builder().test1("a").build();

        apiTestMapper.insertVo(test);

        // RestTemplate 사용
        List<String> list = batchApiSyncService.batchNameSelectList();

        // Webclient 사용
        // List<String> list = batchApiAsyncService.batchNameSelectList();

        log.debug("batchNameSelectList :: {}", list);

        return list;
    }

}
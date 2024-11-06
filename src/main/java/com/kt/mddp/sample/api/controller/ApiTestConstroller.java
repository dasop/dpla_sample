package com.kt.mddp.sample.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kt.dpla.support.lamp.annotation.LampDisable;
import com.kt.mddp.api.comm.vo.ResponseVo;
import com.kt.mddp.sample.api.service.ApiTestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "api", description = "api test")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/sample")
public class ApiTestConstroller {

    private final ApiTestService apiTestService;

    @LampDisable
    @Operation(summary = "배치스텝정보 조회", description = "배치스텝정보 조회")
    @GetMapping(path = "/test")
    public ResponseEntity<ResponseVo<List<String>>> test() {
        List<String> list = apiTestService.test();

        return ResponseEntity.ok(ResponseVo.success(list));
    }
}
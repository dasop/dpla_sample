package com.kt.mddp.sample.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kt.mddp.api.comm.vo.ResponseVo;
import com.kt.mddp.sample.api.service.MaskingTestService;
import com.kt.mddp.sample.api.vo.MaskingTestReqVo;
import com.kt.mddp.sample.api.vo.MaskingTestResVo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "api", description = "api test")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/sample")
public class MaskingTestConstroller {

    private final MaskingTestService maskingTestService;

    @Operation(summary = "배치스텝정보 조회", description = "배치스텝정보 조회")
    @GetMapping(path = "/masking")
    public ResponseEntity<ResponseVo<MaskingTestResVo>> masking(MaskingTestReqVo vo) {
        MaskingTestResVo ret = maskingTestService.test(vo);

        log.debug("after masking test :: {} ", ret);

        return ResponseEntity.ok(ResponseVo.success(ret));
    }
}
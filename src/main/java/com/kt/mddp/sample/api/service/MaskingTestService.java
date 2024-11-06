package com.kt.mddp.sample.api.service;

import org.springframework.stereotype.Service;

import com.kt.dpla.support.masking.MaskingTarget;
import com.kt.mddp.sample.api.vo.MaskingTestReqVo;
import com.kt.mddp.sample.api.vo.MaskingTestResVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MaskingTestService {

    @MaskingTarget
    public MaskingTestResVo test(MaskingTestReqVo vo) {
        log.debug("input phone number :: {} ", vo);

        MaskingTestResVo res = new MaskingTestResVo();
        res.setMblNo(vo.getMblNo());

        log.debug("output phone number :: {} ", res);

        return res;
    }

}
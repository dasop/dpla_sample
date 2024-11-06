package com.kt.dpla.support.masking.service.impl;

import org.springframework.stereotype.Service;

import com.kt.dpla.support.masking.service.MaskingService;

@Service
public class DefaultMaskingService implements MaskingService {

    @Override
    public String doMasking(String target) {
        return target;
    }
}
package com.kt.dpla.support.masking.service.impl;

import com.kt.dpla.support.masking.service.MaskingService;
import org.springframework.stereotype.Service;

@Service
public class PasswordMaskingService implements MaskingService {

    @Override
    public String doMasking(String target) {
        return "********";
    }
}
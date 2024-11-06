package com.kt.dpla.support.masking.service.impl;

import com.kt.dpla.support.masking.service.MaskingService;
import org.springframework.stereotype.Service;

@Service
public class PassportMaskingService implements MaskingService {

    @Override
    public String doMasking(String target) {
        return target.replaceAll("(M[0-9]{4})([0-9]{4})",  "$1****");
    }
}
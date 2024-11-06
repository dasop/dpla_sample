package com.kt.dpla.support.masking.service.impl;

import com.kt.dpla.support.masking.service.MaskingService;
import org.springframework.stereotype.Service;

@Service
public class ResidentNumberMaskingService implements MaskingService {

    @Override
    public String doMasking(String target) {
        return target.replaceAll("([0-9]{6})-([1-4]{1})([0-9]{6})", "$1-$2******");
    }
}
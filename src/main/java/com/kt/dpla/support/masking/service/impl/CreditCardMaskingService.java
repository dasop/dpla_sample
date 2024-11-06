package com.kt.dpla.support.masking.service.impl;

import com.kt.dpla.support.masking.service.MaskingService;
import org.springframework.stereotype.Service;

@Service
public class CreditCardMaskingService implements MaskingService {

    @Override
    public String doMasking(String target) {
        return target.replaceAll("(\\d{4})-(\\d{2})(\\d{2})-(\\d{4})-(\\d{3})(\\d{1})", "$1-$2**-****-***$6");
    }
}
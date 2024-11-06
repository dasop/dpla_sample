package com.kt.dpla.support.masking.service.impl;

import com.kt.dpla.support.masking.service.MaskingService;
import org.springframework.stereotype.Service;

@Service
public class IpMaskingService implements MaskingService {

    @Override
    public String doMasking(String target) {
        return target.replaceAll("(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)"
                , "***.$2.***.$4");
    }
}
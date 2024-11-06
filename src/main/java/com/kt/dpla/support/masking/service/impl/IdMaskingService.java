package com.kt.dpla.support.masking.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.kt.dpla.support.masking.service.MaskingService;

@Service
public class IdMaskingService implements MaskingService {

    @Override
    public String doMasking(String target) {
        if (StringUtils.isEmpty(target)) {
            return "";
        }
        String trimmedTarget = StringUtils.trim(target);

        int length = trimmedTarget.length();
        StringBuffer buffer = new StringBuffer();
        if (length < 4) {
            buffer.append("***");
        } else {
            buffer.append(trimmedTarget.substring(0, length - 3)).append("***");
        }

        return buffer.toString();
    }
}
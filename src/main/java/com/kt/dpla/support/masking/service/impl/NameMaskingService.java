package com.kt.dpla.support.masking.service.impl;

import com.kt.dpla.support.masking.service.MaskingService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class NameMaskingService implements MaskingService {

    @Override
    public String doMasking(String target) {
        if (StringUtils.isEmpty(target)) {
            return "";
        }
        String trimmedTarget = StringUtils.trim(target);
        int length = trimmedTarget.length();
        int cutLength = Math.abs(length / 3);

        StringBuffer buffer = new StringBuffer();
        if (length < 2) {
            buffer.append("**");
        } else if (length == 2) {
            buffer.append(trimmedTarget.substring(0, 1)).append("*");
        } else {
            buffer.append(trimmedTarget.substring(0, length - cutLength)).append(Stream.generate(() -> "*").limit(cutLength).collect(Collectors.joining()));
        }

        return buffer.toString();
    }
}
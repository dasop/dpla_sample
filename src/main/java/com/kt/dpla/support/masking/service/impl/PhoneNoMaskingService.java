package com.kt.dpla.support.masking.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.kt.dpla.support.masking.service.MaskingService;

@Service
public class PhoneNoMaskingService implements MaskingService {

    @Override
    public String doMasking(String target) {
        if (StringUtils.isEmpty(target)) {
            return "";
        }

        StringBuffer buffer = new StringBuffer();
        String replacedDash = trimAndReplaceDash(target);

        int length = replacedDash.length();
        if (length < 4) {
            buffer.append("***");
        } else {
            int subLength = length - 3;
            String end = replacedDash.substring(subLength, length);

            if (length == 10) {
                buffer.append(replacedDash.substring(0, 3)).append("-").append(replacedDash.substring(3, 4)).append("**-*").append(end);
            } else if (length == 11) {
                buffer.append(replacedDash.substring(0, 3)).append("-").append(replacedDash.substring(3, 5)).append("**-*").append(end);
            } else {
                buffer.append(replacedDash.substring(0, 3)).append("-").append(replacedDash.substring(3, 5)).append("**-*").append(end);
            }
        }

        return buffer.toString();
    }

    private String trimAndReplaceDash(String target) {
        String temp = StringUtils.trim(target);
        if (temp.indexOf("-") > -1) {
            temp = temp.replaceAll("-", "");
        }
        return temp;
    }
}
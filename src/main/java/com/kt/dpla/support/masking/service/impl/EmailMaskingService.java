package com.kt.dpla.support.masking.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.kt.dpla.support.masking.service.MaskingService;

@Service
public class EmailMaskingService implements MaskingService {

    @Override
    public String doMasking(String target) {
        if (StringUtils.isEmpty(target)) {
            return "";
        }
        String[] mail = StringUtils.trim(target).split("@");

        int length = mail[0].length();

        StringBuffer buffer = new StringBuffer();
        if (mail.length > 1 && length > 0 && mail[1] != null) {
            if (length < 4) {
                buffer.append("***@").append(mail[1]);
            } else {
                buffer.append(mail[0].substring(0, length - 3)).append("***@").append(mail[1]);
            }
        } else {
            buffer.append("***");
        }

        return buffer.toString();
    }
}
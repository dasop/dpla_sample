package com.kt.dpla.support.masking.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.kt.dpla.support.masking.service.MaskingService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AddressMaskingService implements MaskingService {

    @Override
    public String doMasking(String target) {
        if (StringUtils.isEmpty(target)) {
            return "";
        }

        String regex = "(([가-힣]+(d|d(,|.)d|)+(읍|면|동|가|리))(^구|)((d(~|-)d|d)(가|리|)|)) ([ ](산(d(~|-)d|d))|)|(([가-힣]|(d(~|-)d)|d)+(로|길))";
        Matcher matcher = Pattern.compile(regex).matcher(target);
        if (matcher.find()) {
            String addr1 = target.substring(0, matcher.end());
            String addr2 = target.substring(matcher.end());
            log.debug(addr1);
            log.debug(addr2);

            return addr1 + addr2.replaceAll("\\S", "*");
        }

        return target;
    }
}
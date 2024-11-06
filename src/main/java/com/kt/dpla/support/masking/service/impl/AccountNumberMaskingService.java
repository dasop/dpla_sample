package com.kt.dpla.support.masking.service.impl;

import com.kt.dpla.support.masking.service.MaskingService;
import org.springframework.stereotype.Service;

@Service
public class AccountNumberMaskingService implements MaskingService {

    @Override
    public String doMasking(String target) {
        char[] charArray = target.toCharArray();
        int left = 6;
        for (int i = 0; i < charArray.length; i++) {
            if(charArray[i] != '-' ){
                if(left > 0){
                    left--;
                    continue;
                }
                charArray[i] = '*';
            }
        }
        return String.valueOf(charArray);
    }
}
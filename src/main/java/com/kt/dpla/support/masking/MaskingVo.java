package com.kt.dpla.support.masking;

import com.kt.dpla.support.lamp.annotation.security.LampEncryptTargetField;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MaskingVo {
    private String maskingYn = "Y";
    @LampEncryptTargetField
    private String password;
    private String retvWhySbst;

    public static MaskingVo withMasking() {
        return new MaskingVo("Y","","");
    }
}
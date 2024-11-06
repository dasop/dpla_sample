package com.kt.mddp.sample.api.vo;

import com.kt.dpla.support.masking.field.MaskingRequired;
import com.kt.dpla.support.masking.field.MaskingType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Setter
@Getter
public class MaskingTestResVo {
    @MaskingRequired(type = MaskingType.PHONE_NO)
    private String mblNo;
}

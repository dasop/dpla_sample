package com.kt.dpla.support.lamp.annotation.security;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kt.dpla.support.lamp.service.EncryptionTargetSerializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 필수 암호화 대상 필드인 경우 해당 Annotation 표시
 *  - 필수암호화대상: 고유식별자(주민등록번호, 여권번호, 운전면허번호, 외국인등록번호), 비밀번호, 바이오정보, 신용정보(계좌번호, 신용카드번호 등), 위치정보
 * @return
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = EncryptionTargetSerializer.class)
public @interface LampEncryptTargetField {
}

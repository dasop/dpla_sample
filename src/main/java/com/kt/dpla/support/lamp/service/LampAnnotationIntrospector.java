package com.kt.dpla.support.lamp.service;

import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.kt.dpla.support.lamp.annotation.security.LampEncryptTargetField;

import java.lang.annotation.Annotation;

/**
 * LAMP 보안로그 필수암호화 대상 필드에 대한 custom serializer 적용을 방지하기 위한 JacksonAnnotationIntrospector 클래스 
 */
public class LampAnnotationIntrospector extends JacksonAnnotationIntrospector {
        @Override
        protected <A extends Annotation > A _findAnnotation(final Annotated annotated, final Class<A> annoClass) {
        if (!annotated.hasAnnotation(LampEncryptTargetField.class)) {
            return super._findAnnotation(annotated, annoClass);
        }
        return null;
    }
}

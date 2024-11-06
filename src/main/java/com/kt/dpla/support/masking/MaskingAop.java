package com.kt.dpla.support.masking;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.kt.dpla.support.masking.field.MaskingRequired;
import com.kt.dpla.support.masking.service.MaskingService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class MaskingAop {


    @AfterReturning(pointcut = "@annotation(com.kt.dpla.support.masking.MaskingTarget)", returning = "result")
    public void maskingByAnnotation(JoinPoint joinPoint, Object result) throws IllegalArgumentException, IllegalAccessException {
        log.debug("=====================Masking : maskingByArgument Start=====================");
        if (result == null) {
            return;
        }
        MaskingVo maskingVo = getMaskingVo(joinPoint);

        maskingProcess(joinPoint.getArgs(), result, maskingVo);
        log.debug("=====================Masking : maskingByArgument End=====================");
    }

    @AfterReturning(pointcut = "execution(* com.kt..service..*(.., MaskingVo+, ..)) ", returning = "result")
    public void maskingByArgument(JoinPoint joinPoint, Object result) throws IllegalArgumentException, IllegalAccessException {
        log.debug("=====================Masking : maskingByArgument Start=====================");
        if (result == null) {
            return;
        }
        MaskingVo maskingVo = Arrays.stream(joinPoint.getArgs())
                .filter(arg -> arg instanceof MaskingVo)
                .map(arg -> (MaskingVo) arg)
                .findFirst()
                .orElseGet(MaskingVo::withMasking);
        maskingProcess(joinPoint.getArgs(), result, maskingVo);
        log.debug("=====================Masking : maskingByArgument End=====================");
    }

    private MaskingVo getMaskingVo(JoinPoint joinPoint) throws IllegalAccessException {
        for (Object arg : joinPoint.getArgs()) {
            Optional<Field> find = Arrays.stream(arg.getClass()
                            .getDeclaredFields())
                    .filter(field -> field.getType() == MaskingVo.class)
                    .peek(field -> field.setAccessible(true))
                    .findFirst();
            if (!find.isEmpty()) {
                return (MaskingVo) find.get()
                        .get(arg);
            }
        }
        return MaskingVo.withMasking();
    }

    private void maskingProcess(Object[] args, Object result, MaskingVo maskingVo) throws IllegalAccessException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String maskingYn = maskingVo.getMaskingYn();

        if (result instanceof Collection) {
            if (doMasking(maskingYn)) {
                for (Object obj : (Collection<Object>) result) {
                    maskingFields(obj);
                }
            }
        } else {
            if (doMasking(maskingYn)) {
                maskingFields(result);
            }
        }
        log.debug("=====================Masking : after End=====================");
    }

    private boolean doMasking(String maskingYn) {
        return StringUtils.isEmpty(maskingYn) || !maskingYn.equals("N");
    }

    private void maskingFields(Object result) throws IllegalAccessException {
        Field[] fields = result.getClass()
                .getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(result);
            if (value == null) {
                continue;
            }
            String val = String.valueOf(value);
            MaskingRequired masking = field.getAnnotation(MaskingRequired.class);
            if (masking != null) {
                MaskingService maskingService = masking.type()
                        .getMaskingService();
                field.set(result, maskingService.doMasking(val));
            }
        }
    }

}

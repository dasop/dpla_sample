package com.kt.dpla.support.lamp.aop;

import com.kt.dpla.support.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Aspect
@Order(1)
@Component
public class UUIDAop {

    @Pointcut("execution(* com.kt.common..controller..*(..))")
    public void setUUID() {
        log.debug("===================== UUIDAop =====================");
    }

    @Before("setUUID()")
    public void before(JoinPoint joinPoint) {
        SessionUtil.setAttribute("TRANSACTION_ID", UUID.randomUUID());
        log.info("================== Method : {}, TransactionId : {} =================", joinPoint.getSignature().getName(), SessionUtil.getAttribute("TRANSACTION_ID").toString());
    }
}

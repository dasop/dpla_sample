package com.kt.dpla.support.lamp.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.kt.dpla.support.exception.CustomException;
import com.kt.dpla.support.lamp.service.LampLogService;
import com.kt.dpla.support.lamp.type.ResponseType;
import com.kt.dpla.support.lamp.vo.Response;
import com.kt.dpla.support.util.SessionUtil;
import com.kt.mddp.api.comm.vo.ResponseVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Order(2)
@Component
@RequiredArgsConstructor
public class LampLogControllerAop {

    private final LampLogService lampLogService;
    private final MessageSource messageSource;

    @Pointcut("execution(public * com.kt..controller..*(..))")
    public void lampLog() {
        log.debug("lampLog pointcut");
    }

    @Before("lampLog()")
    public void before(JoinPoint joinPoint) {
        log.debug("=====================Lamp : Before Start=====================");
        lampLogService.logInReq(joinPoint.getArgs());
        log.debug("=====================Lamp : Before Start=====================");
    }

    @AfterReturning(pointcut = "lampLog()", returning = "result")
    public void after(Object result) {
        log.debug("=====================Lamp : After Start=====================");
        lampLogService.logInRes(result, new Response(ResponseType.INFORMATION, "", "", ""));
        log.debug("=====================Lamp : After End=====================");
    }

//    @AfterReturning(pointcut = "execution(* com.kt.dpla.support.exception.GlobalExceptionHandler.*(..))", returning = "result")
    public void afterThrowingEx(ResponseEntity<ResponseVo> result) {
        log.debug("=====================Lamp : afterThrowing Start=====================");
        log.debug("TRANSACTION_ID :: {}", SessionUtil.getAttribute("TRANSACTION_ID"));
        ResponseVo responseVo = result.getBody();
        lampLogService.logInRes(result, new Response(responseVo.getCode()
                .isBusinessError() ? ResponseType.BUSINESS_ERROR : ResponseType.SYSTEM_ERROR, responseVo.getCode()
                .getCode(), responseVo.getMessage(), ""));
        log.debug("=====================Lamp : afterThrowing End=====================");
    }

    /**
     * AfterThrowing 으로 IN_RES 로그 찍는 경우 사용
     *
     * @param exception
     */
    @AfterThrowing(pointcut = "lampLog() ", throwing = "exception")
    public void afterThrowingEx(Exception exception) {
        log.debug("=====================Lamp : afterThrowing Start=====================");
        log.debug("TRANSACTION_ID :: {}", SessionUtil.getAttribute("TRANSACTION_ID"));
        if (exception instanceof CustomException) {
            CustomException customException = (CustomException) exception;
            String msg = messageSource.getMessage(customException.getErrMsgCd(), customException.getErrMsg(), null);
            lampLogService.logInRes(customException.getMessage(), new Response(customException.getErrCd()
                    .isBusinessError() ? ResponseType.BUSINESS_ERROR : ResponseType.SYSTEM_ERROR, customException.getErrCd()
                    .getCode(), msg, ""));
        } else {
            lampLogService.logInRes(String.valueOf(exception.getCause()), new Response(ResponseType.SYSTEM_ERROR, "500", String.valueOf(exception.getCause()), ""));
        }
        log.debug("=====================Lamp : afterThrowing End=====================");
    }
}
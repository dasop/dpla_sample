package com.kt.dpla.support.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.kt.mddp.api.comm.vo.ResponseCode;
import com.kt.mddp.api.comm.vo.ResponseVo;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(value = { CustomException.class })
    protected ResponseEntity<ResponseVo> handleCustomException(CustomException ex) {
        log.error("handleCustomException :: {}/{}", ex.getErrCd(), ex.getErrMsg());
        String msg = messageSource.getMessage(ex.getErrMsgCd(), ex.getErrMsg(), null);
        log.error("messageSource :: {}", msg);

        return ResponseVo.toResponseEntity(HttpStatus.BAD_REQUEST, ex.getErrCd(), msg);
    }

    @ExceptionHandler(value = { DataAccessException.class })
    protected ResponseEntity<ResponseVo> handleDataAccessException(DataAccessException ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        log.error("handleDataAccessException :: {}", ex);

        return ResponseVo.toResponseEntity(status, ResponseCode.COMMON_DPLA_ERROR, "[DB]처리중 오류가발생하였습니다.");
    }

    @ExceptionHandler(value = { TransactionException.class })
    protected ResponseEntity<ResponseVo> handleTransactionException(TransactionException ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        log.error("TransactionException :: {}", ex);

        return ResponseVo.toResponseEntity(status, ResponseCode.COMMON_DPLA_ERROR, "[TimeOut] 오류가 발생하였습니다.");
    }


    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<ResponseVo> handleException(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        log.error("Exception :: {}", ex);

        return ResponseVo.toResponseEntity(status, ResponseCode.ERROR, "예기치 않은 오류가 발생하였습니다.");
    }
}

package com.kt.dpla.support.lamp.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kt.dpla.support.exception.CustomException;
import com.kt.dpla.support.lamp.vo.Caller;
import com.kt.dpla.support.lamp.vo.Destination;
import com.kt.dpla.support.lamp.vo.Host;
import com.kt.dpla.support.lamp.vo.LampLog;
import com.kt.dpla.support.lamp.vo.Response;
import com.kt.dpla.support.lamp.vo.Security;
import com.kt.dpla.support.lamp.vo.User;
import com.kt.dpla.support.util.SessionUtil;
import com.kt.mddp.api.comm.vo.ResponseCode;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LampLogService {

    @Value("${dpla.support.serviceCd}")
    private String serviceCd;
    @Value("${lamp.serviceDomain}")
    private String serviceDomain;

    private final ObjectMapper mapper;
    private static final Logger LAMP_LOGGER = LoggerFactory.getLogger("lampLogger");

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final String TRANSACTION_ID = "TRANSACTION_ID";
    private static final String SEQ = "SEQ";
    private static final String BIZ_TRANSACTION_ID = "BIZ_TRANSACTION_ID";

    private static final String HOST_NAME = getHostName();
    private static final String HOST_IP = getHostIp();
    private static final String SECURITY = "LAMP_SECURITY";
    private static final String USER = "LAMP_USER";
    private static final String START_TIME = "START_TIME";

    public void logInReq(Object... args) {
        LampLog lampLog = createDefaultInstance();
        lampLog.setInReq(serializeToString(args));
        lampLog.setSeq(increaseAndSaveSeq(getHeaderOrDefaultSeq()));
        writeLog(lampLog);
    }

    public void logInRes(Object result, Response response) {
        LampLog lampLog = createDefaultInstance();
        lampLog.setInRes(response, serializeToString(result));

        String increasedSeq = increaseAndSaveSeq(getRequestScopeSeq());
        lampLog.setSeq(increasedSeq);

        HttpServletResponse servletResponse = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        servletResponse.addHeader(SEQ, increasedSeq);
        writeLog(lampLog);
    }

    public void logOutReq(Object body, Destination destination) {
        LampLog lampLog = createDefaultInstance();
        lampLog.setOutReq(destination, body);
        lampLog.setSeq(increaseAndSaveSeq(getRequestScopeSeq()));
        writeLog(lampLog);
    }

    public void logOutRes(Object result, HttpHeaders headers, Response response) {
        LampLog lampLog = createDefaultInstance();
        lampLog.setOutRes(response, serializeToString(result));
        lampLog.setSeq(increaseAndSaveSeq(getHeaderOrRequestScopeSeq(headers)));
        writeLog(lampLog);
    }

    public void logBeforeInMsgSecurity(User user, Security security) {
        setField(SECURITY, security);
        setField(USER, user);
        setField(START_TIME,System.currentTimeMillis());
    }

    public void logAfterInMsgSecurityReadOrExport(Security security, Response response) {
        Security originSecurity = getField(SECURITY);
        originSecurity.setTarget(security.getTarget());
        originSecurity.setMultiTargetCount(security.getMultiTargetCount());
        originSecurity.setPersonalInfoList(security.getPersonalInfoList());
        logAfterInMsgSecurity(response);
    }

    public void logAfterInMsgSecurity(Response response) {
        Long executionTime = System.currentTimeMillis() - (Long) getField(START_TIME);
        response.setDuration(String.valueOf(executionTime));
        LampLog lampLog = createDefaultInstance();
        lampLog.setInMsg(response, null);
        lampLog.setSecurity(getField(SECURITY));
        lampLog.setUser(getField(USER));

        String seq = increaseAndSaveSeq(getHeaderOrDefaultSeq());
        lampLog.setSeq(seq);
        HttpServletResponse servletResponse = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        servletResponse.addHeader(SEQ, seq);
        writeLog(lampLog);
    }

    public void setOutReqHeaders(HttpHeaders headers) {
        headers.add(TRANSACTION_ID, getField(TRANSACTION_ID));
        headers.add(BIZ_TRANSACTION_ID, getField(BIZ_TRANSACTION_ID));
        headers.add(SEQ, String.valueOf(getRequestScopeSeq()));
    }

    private LampLog createDefaultInstance() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        LampLog lampLog = LampLog.builder()
                .timestamp(FORMATTER.format(LocalDateTime.now()))
                .service(serviceCd)
                .operation(request.getRequestURI())
                .bizTransactionId(getExistBizTransactionId(request))
                .transactionId(getTransactionId(request))
                .caller(new Caller(request.getRemoteHost(), request.getRemoteAddr()))
                .host(new Host(HOST_NAME, HOST_IP))
                .device(null)
                .url(getFullPath(request))
                .serviceDomain(serviceDomain)
                .build();
        return lampLog;
    }

    private void writeLog(LampLog lampLog) {
        try {
            LAMP_LOGGER.info(mapper.writeValueAsString(lampLog));
        } catch (JsonProcessingException e) {
            log.error("error = {}", e);
        }
    }

    private String getTransactionId(HttpServletRequest request) {
        String tranId = request.getHeader(TRANSACTION_ID);
        if (StringUtils.isNotEmpty(tranId)) {
            return tranId;
        }
        if (SessionUtil.getAttribute(TRANSACTION_ID) != null) {
            return SessionUtil.getAttribute(TRANSACTION_ID)
                    .toString();
        }
        return UUID.randomUUID()
                .toString();
    }

    private String getExistBizTransactionId(HttpServletRequest request) {
        return request.getHeader(BIZ_TRANSACTION_ID);
    }

    public String serializeToString(Object... args) {
        if (args.length == 0) {
            return "";
        }
        return Arrays.stream(args)
                .map(arg -> serializeObjectOrGetClass(arg))
                .collect(Collectors.joining("|"));
    }

    public String getPersonalInfoList(Object... args) {
        List<String> personalInfoList = new ArrayList<>();
        for (Object arg : args) {
            try {
                String collect = String.valueOf(mapper.convertValue(arg, Map.class)
                        .entrySet()
                        .stream()
                        .map(v -> ((Map.Entry) v).getKey() + ":" + String.valueOf(((Map.Entry) v).getValue()))
                        .collect(Collectors.joining(", ")));
                personalInfoList.add(collect);
                } catch (Exception e) {
                    log.info("처리 불가 클래스 : {}", e.getMessage());
            }
        }
        return personalInfoList.stream().collect(Collectors.joining(", "));
    }

    public String getPersonalInfoListForReadOrExport(Object arg, boolean isMultiTarget) {
        if(isMultiTarget){
            return String.valueOf(mapper.convertValue(getMultiTargetObject(arg), Map.class)
                    .entrySet()
                    .stream()
                    .map(v -> ((Map.Entry) v).getKey())
                    .collect(Collectors.joining(", ")));
        } else{
            return getPersonalInfoList(arg);
        }
    }

    private Object getMultiTargetObject(Object arg) {
        if (arg instanceof Collection && !((Collection) arg).isEmpty()) {
            return ((Collection) arg).stream()
                    .findFirst()
                    .get();
        }
        throw new CustomException(ResponseCode.COMMON_DPLA_ERROR, "dpla.etc.0001");
    }

    private int getHeaderOrDefaultSeq() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return NumberUtils.toInt(request.getHeader(SEQ), 0);
    }

    private int getRequestScopeSeq() {
        return getField(SEQ);
    }

    private int getHeaderOrRequestScopeSeq(HttpHeaders headers) {
        Optional<String> getAnySeq = headers.get(SEQ)
                .stream()
                .findFirst();

        if (!getAnySeq.isEmpty()) {
            return NumberUtils.toInt(getAnySeq.get());
        }
        return getField(SEQ);
    }

    private String increaseAndSaveSeq(int seq) {
        setField(SEQ, ++seq);
        return String.valueOf(seq);
    }

    private String serializeObjectOrGetClass(Object obj) {
        try {
            if (obj instanceof HttpServletResponse) {
                return obj.toString();
            }
            if (obj instanceof HttpServletRequest) {
                return obj.toString();
            }
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return obj.toString();
        }
    }

    private String getFullPath(HttpServletRequest request) {
        return String.format("%s://%s:%s%s", request.getScheme(), request.getServerName(), request.getServerPort(), request.getRequestURI());
    }

    private static String getHostName() {
        try {
            return InetAddress.getLocalHost()
                    .getHostName();
        } catch (UnknownHostException e) {
            return "localhost";
        }
    }

    private static String getHostIp() {
        try {
            return InetAddress.getLocalHost()
                    .getHostAddress();
        } catch (UnknownHostException e) {
            return "localhost";
        }
    }

    private <R> void setField(String field, R value) {
        RequestContextHolder.getRequestAttributes()
                .setAttribute(field, value, RequestAttributes.SCOPE_REQUEST);
    }

    private <R> R getField(String field) {
        return (R)RequestContextHolder.getRequestAttributes()
                .getAttribute(field, RequestAttributes.SCOPE_REQUEST);
    }
}

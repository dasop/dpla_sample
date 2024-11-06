package com.kt.dpla.support.lamp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kt.dpla.support.exception.CustomException;
import com.kt.dpla.support.lamp.type.ResponseType;
import com.kt.dpla.support.lamp.vo.Destination;
import com.kt.dpla.support.lamp.vo.Response;
import com.kt.mddp.api.comm.vo.ResponseCode;
import com.kt.mddp.api.comm.vo.ResponseVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@Component
@RequiredArgsConstructor
public class DplaLampRestTemplateInterceptor implements ClientHttpRequestInterceptor {

    protected final LampLogService lampLogService;
    protected final ObjectMapper objectMapper;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) {
        log.debug("--------------------- start request---------------------------");
        beforeRequest(request, body);
        try {
            ClientHttpResponse response = execution.execute(request, body);
            afterResponse(response);
            log.debug("--------------------- end request---------------------------");
            return response;
        } catch (IOException e) {
            lampLogService.logOutRes(null, null, new Response(ResponseType.SYSTEM_ERROR, ResponseCode.ERROR.getCode(), e.getMessage(), ""));
            throw new CustomException(ResponseCode.COMMON_DPLA_ERROR,"dpla.etc.0001", e.getMessage());
        }
    }

    private void beforeRequest(HttpRequest request, byte[] body) {
        String host = request.getURI().getHost();
        Destination destination = new Destination(host, getHostIpAddress(host));
        lampLogService.logOutReq(getDeserializeBody(body), destination);
        lampLogService.setOutReqHeaders(request.getHeaders());
    }

    private void afterResponse(ClientHttpResponse response) throws IOException {
        ResponseVo<Object> responseBody = objectMapper.readValue(response.getBody(), ResponseVo.class);
        HttpHeaders headers = response.getHeaders();
        if (responseBody.getCode().isSuccess()) {
            lampLogService.logOutRes(responseBody.getResponse(), headers, new Response(ResponseType.INFORMATION, "", "", ""));
        } else {
            lampLogService.logOutRes(responseBody.getResponse(), headers, new Response(responseBody.getCode().isBusinessError() ? ResponseType.BUSINESS_ERROR : ResponseType.SYSTEM_ERROR,
                    responseBody.getCode().getCode(), responseBody.getCode().getMessage(), ""));
        }
    }

    private Object getDeserializeBody(byte[] body) {
        try {
            return objectMapper.readValue(body, Object.class);
        } catch (IOException e) {
            return null;
        }
    }

    private String getHostIpAddress(String host) {
        try {
            InetAddress byName = InetAddress.getByName(host);
            return byName.getHostAddress();
        } catch (UnknownHostException e) {
            return "unKnownHost";
        }
    }

}

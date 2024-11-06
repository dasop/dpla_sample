package com.kt.mddp.api.comm.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Schema(description = "응답공통")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseVo<T> {

    @Schema(description = "응답 code", required = true)
    private ResponseCode code;

    @Schema(description = "응답 message", required = true)
    private String message;

    @Schema(description = "응답 데이터", required = false)
    private T response;

    public static ResponseVo success(){
        ResponseVo<Map<String, String>> vo = new ResponseVo<>();
        vo.setCode(ResponseCode.SUCCESS);
        vo.setMessage("정상");
        vo.setResponse(new HashMap<String, String>());
        return vo;
    }
    public static <T> ResponseVo success(T response){
        ResponseVo<T> vo = new ResponseVo<>();
        vo.setCode(ResponseCode.SUCCESS);
        vo.setMessage("정상");
        vo.setResponse(response);
        return vo;
    }

    public static ResponseEntity<ResponseVo> toResponseEntity(HttpStatus status, ResponseCode code, String message) {
        return ResponseEntity.status(status).body(ResponseVo.builder().code(code).message(message).response(new HashMap<String, String>()).build());
    }
}
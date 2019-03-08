package com.crm.graduation.crmsystem.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ResponseResult {
    private Integer code;

    private String message;

    private Map<String, Object> result;

    private static ResponseResult responseResult;

    public static ResponseResult result(int code, String message, Map<String, Object> map){
        responseResult = new ResponseResult();
        responseResult.setCode(code);
        responseResult.setMessage(message);
        responseResult.setResult(map);
        return responseResult;
    }

    public static ResponseResult result(int code, String message){
        responseResult = new ResponseResult();
        responseResult.setCode(code);
        responseResult.setMessage(message);
        return responseResult;
    }
    public ResponseResult() {}
}

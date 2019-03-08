package com.crm.graduation.crmsystem.model.utils;

import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 返回结果转换
 */
@Getter
@Setter
public class Result {

    public static Object returnObject(Datas pd, Map map){
        if(pd.containsKey("callback")){
            String callback = pd.get("callback").toString();
            return new JSONPObject(callback, map);
        }else{
            return map;
        }
    }

    private Integer code;

    private String desc;

    private Object object;

    public Result(){
        super();
    }

    public Result(Integer code, String desc, Object object){
        this.code =code;
        this.desc =desc;
        this.object = object;
    }

    public Result(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public static Result returnObj(Integer code,String desc,Object object){
        return new Result(code,desc,object);
    }
    public static Result returnObj(Integer code,String desc){
        return new Result(code,desc);
    }
}

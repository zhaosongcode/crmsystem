package com.crm.graduation.crmsystem.utils;

import com.crm.graduation.crmsystem.exception.CommonExecption;

public class ResultDto {

    public String code;
    public String desc;
    public Object obj;

    public ResultDto() {
    }

    public ResultDto(String code, String desc, Object obj) {
        this.code = code;
        this.desc = desc;
        this.obj = obj;
    }

    public ResultDto(String code, String desc) {
        this.code = code;
        this.desc = desc;
        this.obj = null;
    }

    public ResultDto(CommonExecption common) {
        this.code = common.getExceptionCode();
        this.desc = common.getMessage();
        this.obj = null;
    }

    public ResultDto(CommonExecption common, Object obj) {
        this.code = common.getExceptionCode();
        this.desc = common.getMessage();
        this.obj = obj;
    }

    public static ResultDto success() {
        return new ResultDto("0", "操作成功");
    }

    public static ResultDto success(Object data) {
        return new ResultDto("0", "操作成功", data);
    }

    public static ResultDto error() {
        return new ResultDto("1", "操作失败");
    }

    public static ResultDto error(Object data) {
        return new ResultDto("1", "操作失败", data);
    }

    public void init(String code, String desc, Object obj) {
        this.code = code;
        this.desc = desc;
        this.obj = obj;
    }

    public void init(String code, String desc) {
        this.code = code;
        this.desc = desc;
        this.obj = null;
    }

    public void init(CommonExecption common) {
        this.code = common.getExceptionCode();
        this.desc = common.getMessage();
        this.obj = null;
    }

    public void init(CommonExecption common, Object obj) {
        this.code = common.getExceptionCode();
        this.desc = common.getMessage();
        this.obj = obj;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Object getObj() {
        return this.obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String toString() {
        return "ReturnDto{code='" + this.code + '\'' + ", desc='" + this.desc + '\'' + ", obj=" + this.obj + '}';
    }

    public boolean checkSuccess() {
        return this.getCode().equals("0");
    }
}

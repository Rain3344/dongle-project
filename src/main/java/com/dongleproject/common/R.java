package com.dongleproject.common;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.HashMap;
import java.util.Map;

// <dependency>
//     <groupId>io.swagger</groupId>
//     <artifactId>swagger-annotations</artifactId>
//     <version>1.6.2</version> <!-- 请根据需要选择版本 -->
// </dependency>

//统一返回结果
// @Data
public class R {

    @Schema(description = "是否成功", example = "true")
    private  Boolean success;

    @Schema(description = "返回码", example = "200")
    private Integer code;

    @Schema(description = "返回消息", example = "请求成功")
    private String message;

    @Schema(description = "返回数据")
    private Map<String, Object> data = new HashMap<>();


    //构造方法私有
    private R(){}

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    //成功静态方法
    public static R ok(){
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }

    //失败静态方法
    public static R error(){
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }

    public R success(Boolean success){
        this.setSuccess(success);
        return this;
    }
    public R message(String message){
        this.setMessage(message);
        return this;
    }
    public R code(Integer code){
        this.setCode(code);
        return this;
    }
    public R data(String key, Object value){
        this.data.put(key, value);
        return this;
    }
    public R data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}

package cn.sachin.jaBlog.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;

/**
 * 公共返回类
 */
//注解的作用，序列化时，如果时null,key会被忽略
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Data
public class ServerResult<T> implements Serializable {

    private int status;

    private String message;

    private T data;

    public ServerResult() {}

    public ServerResult(int status) {
        this.status = status;
    }

    public ServerResult(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ServerResult(int status, T data) {
        this.status = status;
        this.data = data;
    }

    public ServerResult(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    //使之不在 json 序列化中
    @JsonIgnore
    public boolean isSuccess() {
        return this.status == ResultCode.SUCCESS.getCode();
    }

    public static <T> ServerResult<T> createResultBySuccess() {
        return new ServerResult<T>(ResultCode.SUCCESS.getCode());
    }

    public static <T> ServerResult<T> createResultBySuccessMsg(String message) {
        return  new ServerResult<T>(ResultCode.SUCCESS.getCode(), message);
    }

    public static <T> ServerResult<T> createResultBySuccess(T data) {
        return new ServerResult<T>(ResultCode.SUCCESS.getCode(), data);
    }

    public static <T> ServerResult<T> createResultBySuccess(String message, T data) {
        return new ServerResult<T>(ResultCode.SUCCESS.getCode(), message, data);
    }


    public static <T> ServerResult<T> createResultByError() {
        return new ServerResult<T>(ResultCode.ERROR.getCode());
    }

    public static <T> ServerResult<T> createResultByErrorMsg(String message) {
        return  new ServerResult<T>(ResultCode.ERROR.getCode(), message);
    }

    public static <T> ServerResult<T> createResultByError(T data) {
        return new ServerResult<T>(ResultCode.ERROR.getCode(), data);
    }

    public static <T> ServerResult<T> createResultByError(String message, T data) {
        return new ServerResult<T>(ResultCode.ERROR.getCode(), message, data);
    }
}

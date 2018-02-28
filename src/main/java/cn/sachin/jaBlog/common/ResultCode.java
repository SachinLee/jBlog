package cn.sachin.jaBlog.common;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS(0, "成功"),
    ERROR(1, "失败"),
    NEED_LOGIN(10, "请登录"),
    ILLEAGAIL_ARGUMENT(-1, "请求错误");

    private final int code;

    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}

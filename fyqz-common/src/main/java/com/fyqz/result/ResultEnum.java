package com.fyqz.result;
/**
 * @author zengchao
 * @date
 */
public enum ResultEnum {
    //异常
    ERROR(500, "未知异常,请联系管理员"),
    //错误
    UNKNOWN_ERROR(-1, "error"),
    //成功
    SUCCESS(1000, "success");
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}
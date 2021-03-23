package com.xwh.registerserver;

public class RegisterResponse {

    public static final String SUCCESS = "success";
    public static final String FAILURE = "fail";

    private String status;

    private Integer code;

    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}

package com.easyshare.pojo.common;

public enum ResultCode {
    SUCCESS(1000),
    ERROR(1001),
    AUTH_FAIL(1002);

    private Integer code;

    ResultCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}

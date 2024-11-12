package com.easyshare.pojo.common;

public enum ActionCode {
    Like(2001),
    Follow(2002),
    Comment(2003);

    private Integer code;

    ActionCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}

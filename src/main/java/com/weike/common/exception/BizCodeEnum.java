package com.weike.common.exception;

public enum BizCodeEnum {
    USERNAME_NO_EXIST_EXCEPTION(400,"用户名不存在"),
    PASSWORD_ERROR_EXCEPTION(401,"密码错误"),
    USERINFO_ERROR_EXCEPTION(402,"用户信息获取失败"),
    UNAUTHORIZED(403,"未授权"),
    UNKNOWN_EXCEPTION(500 , "系统未知异常"),
    ORDER_OUT_EXCEPTION(450 , "订单出库异常");


    private int code;
    private String msg;
    BizCodeEnum(int code ,String msg){
        this.code = code ;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

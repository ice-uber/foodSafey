package com.weike.common.constant;

import lombok.Data;

/**
 * @ClassName: UserConstant
 * @Author: YuanDing
 * @Date: 2024/4/19 20:26
 * @Description:
 */


public enum UserConstant {
    DISTRIBUTION("2" , "配送商"),
    PURCHASER("1" , "采购商"),
    SUPERVISOR("5" , "监管者");

    private String code;
    private String message;

    UserConstant(String code , String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

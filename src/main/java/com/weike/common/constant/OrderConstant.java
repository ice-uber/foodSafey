package com.weike.common.constant;

import lombok.Data;

/**
 * @ClassName: OrderConstant
 * @Author: YuanDing
 * @Date: 2024/4/12 18:57
 * @Description:
 */



public enum OrderConstant {

    UN_ACCEPT(0 , "未受理"),
    UN_SEND(1 , "待发货"),
    SEND(2 , "已发货"),
    RECEIVE(3 , "已签收"),
    FINISH(4 , "已完成"),
    REFUSE_ACCEPT(5 , "拒绝签收"),
    NOT_ACCEPT(6 , "拒绝受理");

    private int code;
    private String message;

    OrderConstant(int code , String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

package com.weike.common.exception;

import lombok.Data;

/**
 * @ClassName: SkuUpFailException
 * @Author: YuanDing
 * @Date: 2024/4/8 15:28
 * @Description: 商品上架失败异常
 */

@Data
public class LoginFailException extends Exception{
    private int code;

    public LoginFailException(String message , int code) {
        super(message);
        this.code = code;
    }



}

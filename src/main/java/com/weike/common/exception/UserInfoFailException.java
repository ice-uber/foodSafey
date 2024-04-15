package com.weike.common.exception;

/**
 * @ClassName: UserInfoFailException
 * @Author: YuanDing
 * @Date: 2024/4/10 20:08
 * @Description:
 * 用户信息获取异常
 */

public class UserInfoFailException extends Exception{
    public UserInfoFailException(String msg) {
        super(msg);
    }
}

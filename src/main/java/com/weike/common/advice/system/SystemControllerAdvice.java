package com.weike.common.advice.system;

import com.weike.common.exception.BizCodeEnum;
import com.weike.common.exception.LoginFailException;
import com.weike.common.exception.UserInfoFailException;
import com.weike.common.utils.R;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName: ControllerAdvice
 * @Author: YuanDing
 * @Date: 2024/4/10 17:17
 * @Description:
 * 捕获全局异常、统一返回异常结果
 */

@Slf4j
@RestControllerAdvice(basePackages = "com.weike.system.controller")
public class SystemControllerAdvice {

    /**
     * 授权过期
     * @param e
     * @return
     */
    @ExceptionHandler(ExpiredJwtException.class)
    public R ExpiredJwtException(ExpiredJwtException e){
        log.error("未授权：", e);
        return R.error(BizCodeEnum.UNAUTHORIZED.getCode(), BizCodeEnum.UNAUTHORIZED.getMsg());
    }

    /**
     * jwt解析失败
     * @param e
     * @return
     */
    @ExceptionHandler(MalformedJwtException.class)
    public R MalformedJwtException(MalformedJwtException e){
        log.error("jwt解析token失败：", e);
        return R.error(BizCodeEnum.USERINFO_ERROR_EXCEPTION.getCode(), BizCodeEnum.USERINFO_ERROR_EXCEPTION.getMsg());
    }


    /**
     * 用户信息获取失败
     * @param e
     * @return
     */
    @ExceptionHandler(UserInfoFailException.class)
    public R UserInfoFailException(UserInfoFailException e){
        log.error("获取用户信息失败：", e);
        return R.error(BizCodeEnum.USERINFO_ERROR_EXCEPTION.getCode(), BizCodeEnum.USERINFO_ERROR_EXCEPTION.getMsg());
    }



    /**
     * 登录失败异常
     * @param e
     * @return
     */
    @ExceptionHandler(LoginFailException.class)
    public R LoginFailException(LoginFailException e){
        log.error("登录失败异常：", e);
        R r = null;
        int code = e.getCode();
        if (code == BizCodeEnum.USERNAME_NO_EXIST_EXCEPTION.getCode()) {
            r = R.error(BizCodeEnum.USERNAME_NO_EXIST_EXCEPTION.getCode(), BizCodeEnum.USERNAME_NO_EXIST_EXCEPTION.getMsg());
        } else {
            r = R.error(BizCodeEnum.PASSWORD_ERROR_EXCEPTION.getCode(), BizCodeEnum.PASSWORD_ERROR_EXCEPTION.getMsg());

        }

        return r;
    }

    /**
     * 未知异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public R exception(Exception e){
        log.error("系统未知异常：", e);
        return R.error(BizCodeEnum.UNKNOWN_EXCEPTION.getCode(), BizCodeEnum.UNKNOWN_EXCEPTION.getMsg());
    }
}

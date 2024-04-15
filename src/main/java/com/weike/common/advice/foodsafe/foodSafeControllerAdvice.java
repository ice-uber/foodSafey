package com.weike.common.advice.foodsafe;

import com.weike.common.exception.BizCodeEnum;
import com.weike.common.exception.NoInputSourceException;
import com.weike.common.utils.R;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName: ControllerAdvice
 * @Author: YuanDing
 * @Date: 2024/4/10 17:16
 * @Description:
 *  一般表全局统一异常返回
 */


@Slf4j
@RestControllerAdvice(basePackages = "com.weike.foodsafe")
public class foodSafeControllerAdvice {

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
     * 订单出库异常
     * @param e
     * @return
     */
    @ExceptionHandler(NoInputSourceException.class)
    public R exception(NoInputSourceException e){
        log.error("订单出库异常：", e);

        return R.error(BizCodeEnum.ORDER_OUT_EXCEPTION.getCode(), BizCodeEnum.ORDER_OUT_EXCEPTION.getMsg())
                .put("data" , e.getNoInputList());
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

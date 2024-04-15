package com.weike.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Interceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @ClassName: LoginProtectInterceptor
 * @Author: YuanDing
 * @Date: 2024/4/10 15:31
 * @Description:
 * 登录保护拦截器
 */

@Slf4j
@Component
public class LoginProtectInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("进入登录保护拦截器");
        // TODO 权限验证

        return true;
    }
}

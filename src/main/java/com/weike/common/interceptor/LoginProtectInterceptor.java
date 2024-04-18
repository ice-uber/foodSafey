package com.weike.common.interceptor;

import com.weike.common.utils.JwtHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.PrintWriter;

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

    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("进入登录保护拦截器");

        // TODO 权限验证
        String token = request.getHeader("token");
        if (token != null) {
            try {
                String userId = jwtHelper.getUserId(token);
                if (jwtHelper.isExpiration(userId)) {
                    ThreadLocal<String> stringThreadLocal = new ThreadLocal<>();
                    stringThreadLocal.set(userId);
                    return true;
                }
            }catch (Exception e) {
                response.setStatus(403);
                return false;
            }

        }


        return false;
    }
}

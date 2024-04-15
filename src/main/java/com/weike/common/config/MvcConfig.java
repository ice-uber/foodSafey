package com.weike.common.config;

import com.weike.common.interceptor.ActionRecordInterceptor;
import com.weike.common.interceptor.LoginProtectInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName: MvcConfig
 * @Author: YuanDing
 * @Date: 2024/4/10 15:38
 * @Description:
 */

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Autowired
    private LoginProtectInterceptor loginProtectInterceptor;

    @Autowired
    private ActionRecordInterceptor actionRecordInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 行为记录拦截器
        registry.addInterceptor(actionRecordInterceptor);

        // 登录保护拦截器注册
        registry.addInterceptor(loginProtectInterceptor );
    }
}

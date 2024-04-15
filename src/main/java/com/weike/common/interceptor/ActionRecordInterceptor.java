package com.weike.common.interceptor;

import com.weike.system.service.LogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @ClassName: ActionRecordInterceptor
 * @Author: YuanDing
 * @Date: 2024/4/10 16:05
 * @Description:
 * 行为记录拦截器
 */

@Slf4j
@Component
public class ActionRecordInterceptor implements HandlerInterceptor {
    @Autowired
    private LogService logService;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("返回行为记录拦截器");

        logService.actionRecord(request);
    }
}

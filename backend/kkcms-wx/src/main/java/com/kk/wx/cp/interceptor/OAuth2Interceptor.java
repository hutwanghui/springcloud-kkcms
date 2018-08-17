/**
 * Copyright (c) 2010-2015 EEFUNG Software Co.Ltd. All rights reserved.
 * 版权所有(c)2010-2015湖南蚁坊软件有限公司。保留所有权利。
 */
package com.kk.wx.cp.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

/**
 * OAuth2Interceptor
 */
public class OAuth2Interceptor implements HandlerInterceptor {
    private static Logger logger = LoggerFactory.getLogger(OAuth2Interceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("进入认证拦截");
        String url = request.getRequestURL().toString();

        HttpSession session = request.getSession();
        // 先判断是否有注解
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        OAuthRequired annotation = method.getAnnotation(OAuthRequired.class);
        if (annotation != null) {
            Object objUid = session.getAttribute("WX_USER_ID");
            if (objUid == null) {
                String resultUrl = request.getRequestURL().toString();
                String param = request.getQueryString();
                if (param != null) {
                    resultUrl += "?" + param;
                }
                try {
                    resultUrl = java.net.URLEncoder.encode(resultUrl, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //请求的路径
                String contextPath = request.getContextPath();
                response.sendRedirect(contextPath + "/wx/auth2?resultUrl=" + resultUrl);
                return false;
            }

        }
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}

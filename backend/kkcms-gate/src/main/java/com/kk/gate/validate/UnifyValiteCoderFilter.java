package com.kk.gate.validate;

import com.alibaba.fastjson.JSON;
import com.kk.common.util.http.HttpInformationHelper;
import com.kk.gate.config.properties.ValidateProperties;
import com.kk.gate.exception.ValidateCodeException;
import com.kk.gate.handler.CustomAuthenticationFailureHandler;
import com.kk.gate.handler.CustomAuthenticationSuccessHandler;
import com.kk.gate.handler.Oauth.CustomOauthAuthenticationFailureHandler;
import com.kk.gate.handler.Oauth.CustomOauthAuthenticationSuccessHandler;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import sun.rmi.runtime.Log;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by msi- on 2018/1/26.
 */
@Component
public class UnifyValiteCoderFilter extends OncePerRequestFilter implements InitializingBean {
    /**
     * web端中的校验码处理器和成功失败处理器配置
     */
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /**
     * 移动端的校验码处理器
     */
    @Autowired
    private ValidateOauthCodeProcessorHolder validateOauthCodeProcessorHolder;
    @Autowired
    private CustomOauthAuthenticationSuccessHandler customOauthAuthenticationSuccessHandler;
    @Autowired
    private CustomOauthAuthenticationFailureHandler customOauthAuthenticationFailureHandler;
    /**
     * 存放所有需要校验验证码的url
     */
    private Map<String, ValidateCodeType> urlMap = new HashMap<>();
    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();
    @Autowired
    private ValidateProperties validateProperties;
    private static Logger logger = LoggerFactory.getLogger(UnifyValiteCoderFilter.class);

    /**
     * 初始化要拦截的url配置信息
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        //提交验证的路径是唯一的，但是获取验证码的路径可以是多个
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
        addUrlToMap(validateProperties.getImageCode().getUrl(), ValidateCodeType.IMAGE);
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
        addUrlToMap(validateProperties.getSmsCode().getUrl(), ValidateCodeType.SMS);
    }

    /**
     * 讲系统中配置的需要校验验证码的URL根据校验的类型放入map
     *
     * @param urlString
     * @param type
     */
    protected void addUrlToMap(String urlString, ValidateCodeType type) {
        if (StringUtils.isNotBlank(urlString)) {
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
            for (String url : urls) {
                urlMap.put(url, type);
            }
        }
    }

    /**
     * 获取校验码的类型，如果当前请求不需要校验，则返回null
     *
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
        ValidateCodeType result = null;
        if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
            Set<String> urls = urlMap.keySet();
            for (String url : urls) {
                if (pathMatcher.match(url, request.getRequestURI())) {
                    result = urlMap.get(url);
                }
            }
        }
        return result;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ValidateCodeType type = getValidateCodeType(request);
        if (type != null) {
            logger.info("看看request里面有什么" + HttpInformationHelper.getBodyData(request));
            logger.info("校验请求(" + request.getRequestURI() + ")中的验证码,验证码类型" + type);
            try {
                if (request.getHeader("devicedId") == null) {
                    //web端
                    logger.info("进入web");
                    validateCodeProcessorHolder.findValidateCodeProcessor(type)
                            .validate(new ServletWebRequest(request, response));
                } else {
                    //移动端
                    logger.info("进入移动");
                    validateOauthCodeProcessorHolder.findOauthValidateCodeProcessor(type)
                            .validate(new ServletWebRequest(request, response));
                }
                logger.info("验证码校验通过");
            } catch (ValidateCodeException exception) {
                customOauthAuthenticationFailureHandler.onAuthenticationFailure(request, response, exception);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

}

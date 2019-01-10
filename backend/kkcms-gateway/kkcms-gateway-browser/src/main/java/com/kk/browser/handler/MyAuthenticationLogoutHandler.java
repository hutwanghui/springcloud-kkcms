package com.kk.browser.handler;

import com.alibaba.fastjson.JSON;
import com.kk.common.util.JsonUtil;
import com.kk.common.util.http.HttpHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hutwanghui on 2019/1/8.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */
@Component("myAuthenticationLogoutHandler")
public class MyAuthenticationLogoutHandler implements LogoutSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Authentication authentication) throws IOException, ServletException {
        String result = JSON.toJSONString(JsonUtil.getSuccessJsonObject(true));
        logger.info("登出操作 result:{}", result);
        if (authentication != null && authentication.getDetails() != null) {
            try {
                //使当前session无效
                httpServletRequest.getSession().invalidate();
                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        HttpHelper.setResponseJsonData(httpServletResponse, result);
    }
}
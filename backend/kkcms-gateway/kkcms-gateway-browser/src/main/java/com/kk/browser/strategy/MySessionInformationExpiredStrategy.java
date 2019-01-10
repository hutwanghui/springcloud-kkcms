package com.kk.browser.strategy;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by hutwanghui on 2019/1/9.
 * email:zjjhwanhui@163.com
 * qq:472860892
 * session并发登录失效策略
 */
public class MySessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        // 该对象能获取到访问失效前的url地址
        event.getResponse().setContentType("application/json;charset=UTF-8");
        event.getResponse().getWriter().write("session并发登录");
    }
}

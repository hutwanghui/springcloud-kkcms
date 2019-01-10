package com.kk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hutwanghui on 2019/1/10.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */
@RestController
public class TestController {
    @Autowired
    OAuth2RestTemplate oAuth2RestTemplate;


    @Value("${messages.url:http://localhost:8888}/api")
    String messagesUrl;

    @RequestMapping("/api")
    String home(Model model) {
        String result = oAuth2RestTemplate.getForObject(messagesUrl + "/2", String.class);
        return result;
    }

    @GetMapping("/user")
    public Authentication user(Authentication user) {
        return user;
    }

}

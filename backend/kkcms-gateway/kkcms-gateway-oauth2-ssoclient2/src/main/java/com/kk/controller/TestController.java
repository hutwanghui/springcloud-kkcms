package com.kk.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hutwanghui on 2019/1/10.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */
@RestController
public class TestController {
    @GetMapping("/user")
    public Authentication user(Authentication user) {
        return user;
    }

    @GetMapping("/api")
    public String api() {
        return "client 2";
    }
}

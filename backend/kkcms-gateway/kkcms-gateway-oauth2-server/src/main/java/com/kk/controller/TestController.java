package com.kk.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hutwanghui on 2019/1/10.
 * email:zjjhwanhui@163.com
 * qq:472860892
 * desc：测试是否实现了保护的api接口
 */
@RestController
public class TestController {
    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable String id) {
        //for debug
        System.out.println("【【【【【【【【进入产品管理】】】】】】】】");
        return "product id : " + id;
    }

    @GetMapping("/order/{id}")
    public String getOrder(@PathVariable String id) {
        //for debug
        System.out.println("【【【【【【【【进入订单管理】】】】】】】】");
        return "order id : " + id;
    }
}

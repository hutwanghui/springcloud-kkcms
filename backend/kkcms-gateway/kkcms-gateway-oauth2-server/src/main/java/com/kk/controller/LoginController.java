package com.kk.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by hutwanghui on 2019/1/10.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */
@RestController
public class LoginController {
    @GetMapping("/authentication/require")
    public ModelAndView require() {
        return new ModelAndView("/login");
    }
}

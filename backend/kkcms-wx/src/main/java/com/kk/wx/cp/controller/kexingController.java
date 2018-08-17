package com.kk.wx.cp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by msi- on 2018/5/16.
 */
@RestController
public class kexingController {
    @GetMapping("WW_verify_czjCa1zT2278eD1z.txt")
    public String get() {
        return "czjCa1zT2278eD1z";
    }
}

package com.kk.kkcmsmovierecommend.controller;

import com.kk.common.util.http.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hutwanghui on 2018/11/8.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */

@RequestMapping("/test")
@RestController
public class TestController {
    @RequestMapping("te")
    public R te() {
        return R.ok();
    }
}

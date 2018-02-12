package com.kk.gate.config.properties;

import com.kk.gate.config.properties.security.BrowserProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by msi- on 2018/1/23.
 * 封装数据读取读取
 */
@ConfigurationProperties(prefix = "com.kk.security")
public class SecurityProperties {
    private BrowserProperties browser = new BrowserProperties();
    public BrowserProperties getBrowser() {
        return browser;
    }
    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }
}

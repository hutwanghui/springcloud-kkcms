package com.kk.gate.config;

import com.kk.gate.config.properties.SdkProperties;
import com.kk.gate.config.properties.SocialProperties;
import com.kk.gate.config.properties.ValidateProperties;
import com.kk.gate.config.properties.security.BrowserProperties;
import com.kk.gate.config.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by msi- on 2018/1/23.
 * 配置类，读取properties里面定义好的配置信息
 * 使读取器生效
 */

@Configuration
@EnableConfigurationProperties({SecurityProperties.class, ValidateProperties.class, SdkProperties.class, SocialProperties.class})
public class SecurityPropertiesConfig {
    private BrowserProperties browser = new BrowserProperties();
    private ValidateProperties validate = new ValidateProperties();
    private SdkProperties sdk = new SdkProperties();
    private SocialProperties social = new SocialProperties();

    public SdkProperties getSdk() {
        return sdk;
    }

    public void setSdk(SdkProperties sdk) {
        this.sdk = sdk;
    }

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }

    public ValidateProperties getValidate() {
        return validate;
    }

    public void setValidate(ValidateProperties validate) {
        this.validate = validate;
    }

    public SocialProperties getSocial() {
        return social;
    }

    public void setSocial(SocialProperties social) {
        this.social = social;
    }
}

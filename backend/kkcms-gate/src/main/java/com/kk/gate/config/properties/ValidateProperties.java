package com.kk.gate.config.properties;

import com.kk.gate.config.properties.validate.ImageCodeProperties;
import com.kk.gate.config.properties.validate.SmsCodeProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by msi- on 2018/1/24.
 */
@ConfigurationProperties(prefix = "com.kk.validate")
public class ValidateProperties {
    private ImageCodeProperties imageCode = new ImageCodeProperties();
    private SmsCodeProperties smsCode = new SmsCodeProperties();

    public ImageCodeProperties getImageCode() {
        return imageCode;
    }

    public void setImageCode(ImageCodeProperties imageCode) {
        this.imageCode = imageCode;
    }

    public SmsCodeProperties getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(SmsCodeProperties smsCode) {
        this.smsCode = smsCode;
    }
}

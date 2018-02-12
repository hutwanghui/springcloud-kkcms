package com.kk.gate.config.properties.validate;

/**
 * Created by msi- on 2018/1/24.
 * 应用级验证码的默认配置
 */
public class ImageCodeProperties extends SmsCodeProperties {

    public int width = 100;
    public int height = 30;

    public ImageCodeProperties() {
        setSize(4);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

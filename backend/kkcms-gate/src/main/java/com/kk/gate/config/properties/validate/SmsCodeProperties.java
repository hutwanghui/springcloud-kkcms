package com.kk.gate.config.properties.validate;

/**
 * Created by msi- on 2018/1/25.
 */
public class SmsCodeProperties {

    private int size = 6;
    private int expireIn = 300;
    private String url;
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

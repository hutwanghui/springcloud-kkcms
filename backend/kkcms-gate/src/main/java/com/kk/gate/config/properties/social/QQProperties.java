package com.kk.gate.config.properties.social;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * Created by msi- on 2018/1/27.
 * SocialProperties里已有appid和appsecret
 */
public class QQProperties extends SocialProperties {
    private String providerId = "qq";
    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}

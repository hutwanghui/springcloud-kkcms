package com.kk.gate.config.properties;

import com.kk.gate.config.properties.social.QQProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by msi- on 2018/1/27.
 */
@ConfigurationProperties(prefix = "com.kk.social")
public class SocialProperties {
    private String filterProcessesUrl = "/auth";
    private QQProperties qq = new QQProperties();

    public QQProperties getQq() {
        return qq;
    }

    public void setQq(QQProperties qq) {
        this.qq = qq;
    }

    public String getFilterProcessesUrl() {
        return filterProcessesUrl;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }
}

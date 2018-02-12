package com.kk.gate.config.properties.validate;

/**
 * Created by msi- on 2018/1/25.
 */
public class TxMessageProperties {
    private String appKey = "bb458f9ceedd6a8b33008fabcf034829";
    private long appId = 1400065945;
    private int smsModuleId = 81401;
    private int smsSignId = 37034;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public int getSmsModuleId() {
        return smsModuleId;
    }

    public void setSmsModuleId(int smsModuleId) {
        this.smsModuleId = smsModuleId;
    }

    public int getSmsSignId() {
        return smsSignId;
    }

    public void setSmsSignId(int smsSignId) {
        this.smsSignId = smsSignId;
    }
}

package com.kk.gate.config.properties;

import com.kk.gate.config.properties.validate.TxMessageProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by msi- on 2018/1/25.
 */
@ConfigurationProperties(prefix = "com.kk.sdk")
public class SdkProperties {
    private TxMessageProperties txmessage;

    public TxMessageProperties getTxmessage() {
        return txmessage;
    }

    public void setTxmessage(TxMessageProperties txmessage) {
        this.txmessage = txmessage;
    }
}

package com.kk.gate.validate.code.sms.impl;

import com.kk.gate.config.properties.SdkProperties;
import com.kk.gate.validate.code.sms.SmsCodeSender;
import com.github.qcloudsms.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by msi- on 2018/1/25.
 */
@Service
public class DefaultSmsCodeSender implements SmsCodeSender {
    @Autowired
    private SdkProperties sdkProperties;

    @Override
    public void send(String mobile, String code) throws Exception {
        SmsSingleSender sender = new SmsSingleSender((int) sdkProperties.getTxmessage().getAppId(), sdkProperties.getTxmessage().getAppKey());
        ArrayList<String> params = new ArrayList<String>();
        params.add(code);
        params.add("5");
        SmsSingleSenderResult smsSingleSenderResult = sender.sendWithParam("86", mobile, sdkProperties.getTxmessage().getSmsModuleId(), params, "", "", "");
        System.out.print("发送短信验证码完成：" + smsSingleSenderResult.toString());
    }
}

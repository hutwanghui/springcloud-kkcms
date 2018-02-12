package com.kk.gate.validate.code.sms;

/**
 * Created by msi- on 2018/1/25.
 */
public interface SmsCodeSender {

    public void send(String mobile,String code) throws Exception;

}

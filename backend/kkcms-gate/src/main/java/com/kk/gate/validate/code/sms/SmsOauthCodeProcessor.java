package com.kk.gate.validate.code.sms;

import com.kk.gate.validate.SecurityConstants;
import com.kk.gate.validate.impl.AbstractOauthValidateCodeProcessor;
import com.kk.gate.validate.impl.AbstractValidateCodeProcessor;
import com.kk.gate.validate.vo.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created by msi- on 2018/2/5.
 * 移动端短信验证码处理器
 */
@Component("smsValidateOauthCodeProcessor")
public class SmsOauthCodeProcessor extends AbstractOauthValidateCodeProcessor<ValidateCode> {

    @Autowired
    private SmsCodeSender smsCodeSender;

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        String paramName = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), paramName);
        smsCodeSender.send(mobile, validateCode.getCode());
    }
}

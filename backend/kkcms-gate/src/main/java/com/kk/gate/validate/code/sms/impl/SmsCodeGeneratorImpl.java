package com.kk.gate.validate.code.sms.impl;

import com.kk.gate.validate.code.sms.SmsCodeGenerator;
import com.kk.gate.validate.code.ValidateCodeGeneratorImpl;
import org.springframework.stereotype.Service;

/**
 * Created by msi- on 2018/1/26.
 */
@Service("smsValidateCodeGenerator")
public class SmsCodeGeneratorImpl extends ValidateCodeGeneratorImpl implements SmsCodeGenerator {
}

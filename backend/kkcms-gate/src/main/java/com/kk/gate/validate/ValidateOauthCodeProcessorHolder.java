package com.kk.gate.validate;

import com.kk.gate.exception.ValidateCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 通过依赖查找的思想用次类来查找出对应的验证码处理器具体实现类
 */
@Component
public class ValidateOauthCodeProcessorHolder {

    @Autowired
    private Map<String, ValidateOauthCodeProcessor> validateOauthCodeProcessors;

    public ValidateOauthCodeProcessor findOauthValidateCodeProcessor(ValidateCodeType type) {
        return findOauthValidateCodeProcessor(type.toString().toLowerCase());
    }

    public ValidateOauthCodeProcessor findOauthValidateCodeProcessor(String type) {
        String name = type.toLowerCase() + ValidateOauthCodeProcessor.class.getSimpleName();
        ValidateOauthCodeProcessor processor = validateOauthCodeProcessors.get(name);
        if (processor == null) {
            throw new ValidateCodeException("验证码处理器" + name + "不存在");
        }
        return processor;
    }

}

package com.kk.gate.validate.impl;

import com.kk.common.util.ObjectUtil;
import com.kk.gate.exception.ValidateCodeException;
import com.kk.gate.validate.ValidateCodeGenerator;
import com.kk.gate.validate.ValidateCodeType;
import com.kk.gate.validate.ValidateOauthCodeProcessor;
import com.kk.gate.validate.code.image.entity.ImageCode;
import com.kk.gate.validate.vo.ValidateCode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;
import java.util.Map;


/**
 * Created by msi- on 2018/2/4.
 */
public abstract class AbstractOauthValidateCodeProcessor<C extends ValidateCode> implements ValidateOauthCodeProcessor {

    private static Logger logger = LoggerFactory.getLogger(AbstractOauthValidateCodeProcessor.class);

    @Autowired
    private ValidateOauthCodeRepository validateOauthCodeRepository;
    /**
     * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    @Override
    public void create(ServletWebRequest request) throws Exception {
        C validateCode = generate(request);
        save(request, validateCode);
        send(request, validateCode);
    }

    /**
     * 生成校验码
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    private C generate(ServletWebRequest request) throws IOException {
        String type = getValidateCodeType(request).toString().toLowerCase();
        //将CodeProcessor前的类型字段与ValidateCodeGenerator拼接动态调用不同类型的代码生成器
        String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
        if (validateCodeGenerator == null) {
            logger.info("验证码生成器" + generatorName + "不存在");
            throw new ValidateCodeException("1");
        }
        return (C) validateCodeGenerator.generateValidateCode();
    }

    /**
     * 保存校验码
     *
     * @param request
     * @param validateCode
     */
    private void save(ServletWebRequest request, C validateCode) {
        if (validateCode instanceof ImageCode) {
            System.out.println("11111111111111");
//            ValidateCode validateCode_temp = ObjectUtil.copyProperties(validateCode, ValidateCode.class);
            validateOauthCodeRepository.save(request, validateCode, getValidateCodeType(request));
        } else {
            System.out.println("2222222222222222");
            validateOauthCodeRepository.save(request, validateCode, getValidateCodeType(request));
        }
    }

    /**
     * 发送校验码，由子类实现
     *
     * @param request
     * @param validateCode
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

    /**
     * 根据请求的url获取校验码的类型
     *
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        //截取CodeProcessor前的字符串，将其和SESSION的prefix拼接，所以实现类的Processpr写法应统一
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "OauthCodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void validate(ServletWebRequest request) {
        ValidateCodeType processorType = getValidateCodeType(request);
        C codeInRedis = (C) validateOauthCodeRepository.get(request, processorType);
        System.out.print("我来拦截" + request);
        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                    processorType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            logger.info("获取验证码的值失败");
            throw new ValidateCodeException("获取验证码的值失败", "104");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            logger.info("验证码的值不能为空");
            throw new ValidateCodeException("验证码的值不能为空", "105");
        }

        if (codeInRedis == null) {
            logger.info("验证码不存在");
            throw new ValidateCodeException("验证码不存在", "106");
        }

        if (codeInRedis.isExpried()) {
            validateOauthCodeRepository.remove(request, processorType);
            logger.info("验证码过期");
            throw new ValidateCodeException("验证码过期", "107");
        }

        if (!StringUtils.equals(codeInRedis.getCode().toUpperCase(), codeInRequest.toUpperCase())) {
            logger.info("验证码不匹配");
            throw new ValidateCodeException("验证码过期", "108");
        }

        validateOauthCodeRepository.remove(request, processorType);
    }

}

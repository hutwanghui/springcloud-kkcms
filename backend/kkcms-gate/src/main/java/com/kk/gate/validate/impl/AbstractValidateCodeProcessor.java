/**
 *
 */
package com.kk.gate.validate.impl;

import java.io.IOException;
import java.util.Map;

import com.kk.common.util.ObjectUtil;
import com.kk.gate.exception.ValidateCodeException;
import com.kk.gate.validate.ValidateCodeGenerator;
import com.kk.gate.validate.ValidateCodeProcessor;
import com.kk.gate.validate.ValidateCodeType;
import com.kk.gate.validate.code.image.entity.ImageCode;
import com.kk.gate.validate.vo.ValidateCode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 将短信验生成方式和图形验证生成方式的共有部分抽取出来的抽象类，使用的思想是依赖查找到具体的Processor实现类
 * 两者的共性是创建验证码和验证码存入session，所以create方法和
 * save方法在抽象类中实现，发送方式不同：短信验证是调用第三方SDK而图形验证码是将流传回response
 * 还有一点不同在save的时候要判断code的类型，因为BufferImg是不能序列化的
 *
 * @param <C>
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

    private static Logger logger = LoggerFactory.getLogger(AbstractValidateCodeProcessor.class);
    /**
     * 操作session的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
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
            throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
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
            ValidateCode validateCode_temp = ObjectUtil.copyProperties(validateCode, ValidateCode.class);
            sessionStrategy.setAttribute(request, getSessionKey(request), validateCode_temp);
        } else {
            sessionStrategy.setAttribute(request, getSessionKey(request), validateCode);
        }

    }

    /**
     * 构建验证码放入session时的key
     *
     * @param request
     * @return
     */
    private String getSessionKey(ServletWebRequest request) {
        return SESSION_KEY_PREFIX + getValidateCodeType(request).toString().toUpperCase();
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
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void validate(ServletWebRequest request) {

        ValidateCodeType processorType = getValidateCodeType(request);
        String sessionKey = getSessionKey(request);
        C codeInSession = (C) sessionStrategy.getAttribute(request, sessionKey);
        System.out.print("我来拦截111111111");
        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                    processorType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            logger.info("获取验证码的值失败");
            throw new ValidateCodeException("104");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            logger.info("验证码的值不能为空");
            throw new ValidateCodeException("105");
        }

        if (codeInSession == null) {
            logger.info("验证码不存在");
            throw new ValidateCodeException("106");
        }

        if (codeInSession.isExpried()) {
            sessionStrategy.removeAttribute(request, sessionKey);
            logger.info("验证码过期");
            throw new ValidateCodeException("107");
        }

        if (!StringUtils.equals(codeInSession.getCode().toUpperCase(), codeInRequest.toUpperCase())) {
            logger.info("验证码不匹配");
            throw new ValidateCodeException("108");
        }
        sessionStrategy.removeAttribute(request, sessionKey);
    }

}

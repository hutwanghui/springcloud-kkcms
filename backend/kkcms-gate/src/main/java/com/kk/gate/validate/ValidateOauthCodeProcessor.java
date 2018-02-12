package com.kk.gate.validate;

import com.kk.gate.validate.vo.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.ServletRequest;

/**
 * Created by msi- on 2018/2/4.
 */
public interface ValidateOauthCodeProcessor {

    /**
     * 创建校验码
     *
     * @param request
     * @throws Exception
     */
    void create(ServletWebRequest request) throws Exception;
    /**
     * 保存验证码
     * @param request
     * @param code
     * @param type
     */
     void validate(ServletWebRequest request);
}


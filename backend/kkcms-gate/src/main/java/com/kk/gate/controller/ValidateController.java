package com.kk.gate.controller;

import com.kk.gate.validate.SecurityConstants;
import com.kk.gate.validate.ValidateCodeProcessorHolder;
import com.kk.gate.config.properties.SecurityProperties;
import com.kk.gate.validate.ValidateOauthCodeProcessorHolder;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by msi- on 2018/1/23.
 */
@RestController
public class ValidateController {

    //操作session的工具类，需要导入spring-social-web包
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    //日后可以通过读取配置文件来动态的改变验证码的大小
    @Autowired
    private SecurityProperties securityProperties;
    /*    @Autowired
        private ImageCodeGenerator imageCodeGenerator;
        @Autowired
        private ValidateCodeGenerator validateCodeGenerator;
        @Autowired
        private SmsCodeSender smsCodeSender;*/
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;
    @Autowired
    private ValidateOauthCodeProcessorHolder validateOauthCodeProcessorHolder;

    /**
     * 创建验证码，根据验证码类型不同，调用不同的 {@link ValidateCodeProcessor}接口实现
     * 因为获取验证码的前缀都是/code/*所以用这种方法使得代码不需要复写
     *
     * @param request
     * @param response
     * @param type
     * @throws Exception
     */
    @GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{type}")
    @ApiOperation(value = "多种验证码生成")
    public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type)
            throws Exception {
        System.out.print("web端" + "接收前端获取验证码类型：" + type);
        validateCodeProcessorHolder.findValidateCodeProcessor(type).create(new ServletWebRequest(request, response));
    }

    @GetMapping(SecurityConstants.DEFAULT_OAUTH_VALIDATE_CODE_URL_PREFIX + "/{type}")
    @ApiOperation(value = "多种验证码生成")
    public void createOauthCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type)
            throws Exception {
        System.out.println("移动端" + "接收前端获取验证码类型：" + type);
        validateOauthCodeProcessorHolder.findOauthValidateCodeProcessor(type).create(new ServletWebRequest(request, response));
    }
}

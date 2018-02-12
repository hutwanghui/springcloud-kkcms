package com.kk.gate.validate.code.image;

import com.kk.gate.validate.ValidateCodeType;
import com.kk.gate.validate.code.image.entity.ImageCode;
import com.kk.gate.validate.impl.AbstractOauthValidateCodeProcessor;
import com.kk.gate.validate.impl.AbstractValidateCodeProcessor;
import com.kk.gate.validate.vo.ValidateCode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

/**
 * Created by msi- on 2018/1/26.
 * Web端的验证码处理器
 */
@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode>{
    @Override
    protected void send(ServletWebRequest request, ImageCode imageCode) throws Exception {
        System.out.print("web端发送图片");
        ImageIO.write(imageCode.getImage(), "JPEG", request.getResponse().getOutputStream());
    }
}

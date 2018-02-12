package com.kk.gate.validate.code.image;

import com.kk.gate.validate.code.image.entity.ImageCode;
import com.kk.gate.validate.impl.AbstractOauthValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

/**
 * Created by msi- on 2018/2/5.
 * 移动端的验证码处理器
 */
@Component("imageValidateOauthCodeProcessor")
public class ImageOauthCodeProcessor extends AbstractOauthValidateCodeProcessor<ImageCode>{
    @Override
    protected void send(ServletWebRequest request, ImageCode imageCode) throws Exception {
        System.out.print("移动端发送图片");
        ImageIO.write(imageCode.getImage(), "JPEG", request.getResponse().getOutputStream());
    }
}

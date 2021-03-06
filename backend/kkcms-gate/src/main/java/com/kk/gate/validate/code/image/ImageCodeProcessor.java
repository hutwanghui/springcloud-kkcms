package com.kk.gate.validate.code.image;

import com.kk.gate.validate.ValidateCodeType;
import com.kk.gate.validate.code.image.entity.ImageCode;
import com.kk.gate.validate.impl.AbstractOauthValidateCodeProcessor;
import com.kk.gate.validate.impl.AbstractValidateCodeProcessor;
import com.kk.gate.validate.vo.ValidateCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

/**
 * Created by msi- on 2018/1/26.
 * Web端的验证码处理器
 */
@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode>{
    private static Logger logger= LoggerFactory.getLogger(ImageCodeProcessor.class);
    @Override
    protected void send(ServletWebRequest request, ImageCode imageCode) throws Exception {
        System.out.print("web端发送图片");
        try {
            request.getResponse().setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            request.getResponse().setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            request.getResponse().setHeader("Cache-Control", "no-cache");
        ImageIO.write(imageCode.getImage(), "JPEG", request.getResponse().getOutputStream());
        } catch (Exception e) {
            logger.error("获取验证码失败>>>> ", e);
        }
    }
}

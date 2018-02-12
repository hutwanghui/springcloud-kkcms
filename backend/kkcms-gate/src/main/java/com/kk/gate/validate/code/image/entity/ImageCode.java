package com.kk.gate.validate.code.image.entity;

import com.kk.gate.validate.vo.ValidateCode;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * Created by msi- on 2018/1/23.
 * 普通web端登陆，需要用户名+密码+验证码的形式
 */
public class ImageCode extends ValidateCode implements Serializable {
    //展示给用户的图片
    private BufferedImage image;

    public ImageCode(BufferedImage image, String code, int expireIn) {
        super(code, expireIn);
        this.image = image;
    }


    public ImageCode() {
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

}

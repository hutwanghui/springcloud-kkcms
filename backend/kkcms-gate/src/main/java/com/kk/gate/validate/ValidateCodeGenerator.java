package com.kk.gate.validate;

import com.kk.gate.validate.vo.ValidateCode;

import java.awt.*;
import java.io.IOException;

/**
 * Created by msi- on 2018/1/25.
 */
public interface ValidateCodeGenerator {
    public String generateVerifyCode(int verifySize);

    public String generateVerifyCode(int verifySize, String sources);

    public ValidateCode generateValidateCode() throws IOException;

    public Color getRandColor(int fc, int bc);

    public int getRandomIntColor();

    public int[] getRandomRgb();

    public void shear(Graphics g, int w1, int h1, Color color);

    public void shearX(Graphics g, int w1, int h1, Color color);

    public void shearY(Graphics g, int w1, int h1, Color color);
}

package com.kk.gate.validate.code.image;

import com.kk.gate.validate.ValidateCodeGenerator;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by msi- on 2018/1/25.
 */
public interface ImageCodeGenerator extends ValidateCodeGenerator {


    public BufferedImage outputImage(int w, int h, String code) throws IOException;


}

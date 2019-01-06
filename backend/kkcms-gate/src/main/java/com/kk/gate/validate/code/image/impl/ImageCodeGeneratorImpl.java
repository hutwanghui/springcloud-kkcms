package com.kk.gate.validate.code.image.impl;

import com.kk.gate.config.properties.ValidateProperties;
import com.kk.gate.validate.code.image.entity.ImageCode;
import com.kk.gate.validate.code.image.ImageCodeGenerator;
import com.kk.gate.validate.code.ValidateCodeGeneratorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/**
 * 随机图像验证码的生成
 */
@Service(value = "imageValidateCodeGenerator")
public class ImageCodeGeneratorImpl extends ValidateCodeGeneratorImpl implements ImageCodeGenerator {

    private static Logger logger = LoggerFactory.getLogger(ImageCodeGeneratorImpl.class);

    @Resource
    private ValidateProperties validateProperties;
    //使用到Algerian字体，系统里没有的话需要安装字体，字体只显示大写，去掉了1,0,i,o几个容易混淆的字符
    public static final String VERIFY_CODES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static Random random = new Random();

    @Override
    public String generateVerifyCode(int verifySize) {
        return generateVerifyCode(verifySize, VERIFY_CODES);
    }

    @Override
    public String generateVerifyCode(int verifySize, String sources) {
        if (sources == null || sources.length() == 0) {
            sources = VERIFY_CODES;
        }
        int codesLen = sources.length();
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder verifyCode = new StringBuilder(verifySize);
        for (int i = 0; i < verifySize; i++) {
            verifyCode.append(sources.charAt(rand.nextInt(codesLen - 1)));
        }
        return verifyCode.toString();
    }

    @Override
    public ImageCode generateValidateCode() throws IOException {
        logger.info(">>>>>>>>>>>>>>>随机生成的验证码>>>>>>>>>>>>>>");
        String code = generateVerifyCode(validateProperties.getImageCode().getSize());
        logger.info("随机生成的验证码是:" + code + "");
        BufferedImage bufferedImage = outputImage(validateProperties.getImageCode().getWidth(), validateProperties.getImageCode().getHeight(), code);
        logger.info("<<<<<<<<<<<<<<<随机生成的验证码<<<<<<<<<<<<<<<");
        return new ImageCode(bufferedImage, code, validateProperties.getImageCode().getExpireIn());
    }

    /**
     * 输出指定验证码图片流
     *
     * @param w
     * @param h
     * @param code
     * @throws IOException
     */
    @Override
    public BufferedImage outputImage(int w, int h, String code) throws IOException {
        int verifySize = code.length();
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Random rand = new Random();
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color[] colors = new Color[5];
        Color[] colorSpaces = new Color[]{Color.WHITE, Color.CYAN,
                Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE,
                Color.PINK, Color.YELLOW};
        float[] fractions = new float[colors.length];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = colorSpaces[rand.nextInt(colorSpaces.length)];
            fractions[i] = rand.nextFloat();
        }
        Arrays.sort(fractions);

        g2.setColor(Color.GRAY);// 设置边框色
        g2.fillRect(0, 0, w, h);

        Color c = getRandColor(200, 250);
        g2.setColor(c);// 设置背景色
        g2.fillRect(0, 2, w, h - 4);

        //绘制干扰线
        Random random = new Random();
        g2.setColor(getRandColor(160, 200));// 设置线条的颜色
        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(w - 1);
            int y = random.nextInt(h - 1);
            int xl = random.nextInt(6) + 1;
            int yl = random.nextInt(12) + 1;
            g2.drawLine(x, y, x + xl + 40, y + yl + 20);
        }

        // 添加噪点
        float yawpRate = 0.05f;// 噪声率
        int area = (int) (yawpRate * w * h);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(w);
            int y = random.nextInt(h);
            int rgb = getRandomIntColor();
            image.setRGB(x, y, rgb);
        }
        shear(g2, w, h, c);// 使图片扭曲
        g2.setColor(getRandColor(100, 160));
        int fontSize = h - 4;
        g2.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        char[] chars = code.toCharArray();
        for (int i = 0; i < verifySize; i++) {
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(Math.PI / 4 * rand.nextDouble() * (rand.nextBoolean() ? 1 : -1), (w / verifySize) * i + fontSize / 2, h / 2);
            g2.setTransform(affine);
            g2.drawChars(chars, i, 1, ((w - 10) / verifySize) * i + 5, h / 2 + fontSize / 2 - 10);
        }
        g2.dispose();
//        ImageIO.write(image, "jpg", os);
        return image;
    }


}
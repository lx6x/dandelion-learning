package org.dandelion.kaptcha.controller;

import com.google.code.kaptcha.Producer;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 生成验证码
 *
 * @author lx6x
 * @date 2023/11/23
 */
@RestController
public class CaptchaController {

    private static final Logger logger= LoggerFactory.getLogger(CaptchaController.class);

    @Resource
    private Producer producer;
    @GetMapping("/captcha")
    public void createCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ByteArrayOutputStream imgOutputStream = new ByteArrayOutputStream();
        try {
            //生产验证码字符串并保存到session中
            String verifyCode = producer.createText();
            logger.info("当前验证码为：{}",verifyCode);
            BufferedImage challenge = producer.createImage(verifyCode);
            ImageIO.write(challenge, "jpg", imgOutputStream);
        } catch (IllegalArgumentException | IOException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        byte[] captchaOutputStream = imgOutputStream.toByteArray();
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        try (ServletOutputStream responseOutputStream = response.getOutputStream()) {
            responseOutputStream.write(captchaOutputStream);
            responseOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

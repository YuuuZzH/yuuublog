package com.yuuu.controller;

/**
 * @author: yuuu
 * Description: 验证码接口
 * Email: ***REMOVED***
 * CreatedTime: 2023-06-01 14:33:14:33
 */

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.yuuu.constants.SystemConstants;
import com.yuuu.domain.ResponseResult;
import com.yuuu.utils.CaptchaUtil;
import com.yuuu.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @Autowired
    private RedisCache redisCache;

    private DefaultKaptcha producer;

    public CaptchaController(DefaultKaptcha producer) {
        this.producer = producer;
    }
    @GetMapping()
    public ResponseResult captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 生成验证码
        String text = producer.createText();
        BufferedImage image = producer.createImage(text);
        // 将验证码存入 redis
        request.getSession().setAttribute("captcha", text);
        redisCache.setCacheObject(SystemConstants.CAPTCHA_KEY + text, text, 60, TimeUnit.SECONDS);
        // 生成验证码背景图的 base64 编码
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", bos);
        String base64Image = Base64.getEncoder().encodeToString(bos.toByteArray());
        // 返回验证码图片
        Map<String, Object> data = new HashMap<>();
        data.put("captchaCode", text);
        data.put("captchaImageBase64", base64Image);
        return ResponseResult.okResult(data);
    }
}

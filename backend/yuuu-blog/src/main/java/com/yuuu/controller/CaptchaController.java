package com.yuuu.controller;

/**
 * @author: yuuu
 * Description: 验证码接口
 * Email: ***REMOVED***
 * CreatedTime: 2023-06-01 14:33:14:33
 */

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

    @GetMapping
    public ResponseResult getCaptcha() throws IOException {
       
        String captchaCode = CaptchaUtil.generateCaptchaCode();
        // 设置验证码过期时间 60s
        redisCache.setCacheObject(SystemConstants.CAPTCHA_KEY + captchaCode, captchaCode, 60, TimeUnit.SECONDS);

        // 生成验证码图片
        BufferedImage captchaImage = CaptchaUtil.generateCaptchaImage(captchaCode);

        // 将验证码图片转换为Base64编码
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(captchaImage, "png", baos);
        byte[] imageBytes = baos.toByteArray();
        String captchaImageBase64 = Base64.getEncoder().encodeToString(imageBytes);

        // 返回验证码和图片Base64编码给前端
        Map<String, Object> data = new HashMap<>();
        data.put("captchaCode", captchaCode);
        data.put("captchaImageBase64", captchaImageBase64);
        return ResponseResult.okResult(data);
    }

}

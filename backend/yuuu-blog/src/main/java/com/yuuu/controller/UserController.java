package com.yuuu.controller;

import cn.hutool.core.date.DateTime;
import com.yuuu.annotation.SystemLog;
import com.yuuu.constants.SystemConstants;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.entity.User;
import com.yuuu.service.UserService;
import com.yuuu.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject:YuuuBlog
 * @BelongsPackage:com.yuuu.controller
 * @Author:Yuuu。
 * @CreateTime:2023-03-06 10:45
 * @Description:
 * @Version:1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private RedisCache redisCache;


    @GetMapping("/userInfo")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    @SystemLog(businessName = "更新用户信息")
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }

    @PostMapping("/register")
    @SystemLog(businessName = "注册用户")
    public ResponseResult register(@RequestBody User user){
        return userService.register(user);
    }

    @PostMapping("/sendVerificationCode")
    public ResponseResult sendVerificationCode(@RequestParam("email") String email) {//这是后端写好的接口
        // Generate a random verification code
        String verificationCode = String.format("%06d", new Random().nextInt(999999));
        // Save the verification code in Redis with a time-to-live of 5 minutes
        redisCache.setCacheObject(SystemConstants.MAIL_KEY + verificationCode,verificationCode,600, TimeUnit.SECONDS);
        // Send the verification code to the user's email address
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("***REMOVED***");
        message.setTo(email);
        message.setSubject("邂逅相遇，适我愿兮。阁下接住~~~");
        message.setSentDate(new DateTime(Instant.now()));
        message.setText("【YuuuBlog】登录验证码: " + verificationCode + "，10分钟内有效。切勿将验证码提供给他人！");
        System.out.println(message);
        mailSender.send(message);
        return ResponseResult.okResult("验证码已发送，请耐心等待");
    }

}
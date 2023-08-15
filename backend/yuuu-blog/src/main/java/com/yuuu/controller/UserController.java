package com.yuuu.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.yuuu.annotation.SystemLog;
import com.yuuu.constants.SystemConstants;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.dto.ChangePassword;
import com.yuuu.domain.entity.User;
import com.yuuu.enums.AppHttpCodeEnum;
import com.yuuu.exception.SystemException;
import com.yuuu.service.UserService;
import com.yuuu.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;
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
    public ResponseResult sendVerificationCode(@RequestBody Map<String, String> data) {//这是后端写好的接口
        String nemail = data.get("nemail");
        // Generate a random verification code
        String verificationCode = String.format("%06d", new Random().nextInt(999999));
        // Save the verification code in Redis with a time-to-live of 5 minutes
        redisCache.setCacheObject(SystemConstants.MAIL_KEY + verificationCode,verificationCode,600, TimeUnit.SECONDS);
        // Send the verification code to the user's email address
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("***REMOVED***");
        message.setTo(nemail);
        message.setSubject("邂逅相遇，适我愿兮。阁下接住~~~");
        message.setSentDate(new DateTime(Instant.now()));
        message.setText("【YuuuBlog】登录验证码: " + verificationCode + "，10分钟内有效。切勿将验证码提供给他人！");
        System.out.println(message);
        System.out.println(mailSender);
        try {
            mailSender.send(message);
        } catch (MailException e) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_ERROR);
        }
        return ResponseResult.okResult("验证码已发送，请耐心等待");
    }

    @PostMapping("/sendCodeForUpdatePassword")
    public ResponseResult sendCodeForUpdatePassword(@RequestBody Map<String, String> data) {//这是后端写好的接口
        String emailPassword = data.get("emailPassword");
        // Generate a random verification code
        String verificationCode = String.format("%06d", new Random().nextInt(999999));
        // Save the verification code in Redis with a time-to-live of 5 minutes
        redisCache.setCacheObject(SystemConstants.MAIL_KEY + verificationCode,verificationCode,600, TimeUnit.SECONDS);
        // Send the verification code to the user's email address
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("***REMOVED***");
        message.setTo(emailPassword);
        message.setSubject("验证码来咯，这次可不要再忘记密码啦~~~");
        message.setSentDate(new DateTime(Instant.now()));
        message.setText("【YuuuBlog】登录验证码: " + verificationCode + "，10分钟内有效。切勿将验证码提供给他人！高山仰止，景行行止，虽不能至，心向往之。");
        System.out.println(message);
        System.out.println(mailSender);
        try {
            mailSender.send(message);
        } catch (MailException e) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_ERROR);
        }
        return ResponseResult.okResult("验证码已发送，请耐心等待");
    }

    @PostMapping("/updatePassword")
    public ResponseResult updatePassword(@RequestBody ChangePassword user){

        return userService.updatePassword(user);
    }



}
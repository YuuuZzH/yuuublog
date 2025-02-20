package com.yuuu.controller;

import cn.hutool.core.date.DateTime;
import com.yuuu.annotation.SystemLog;
import com.yuuu.constants.SystemConstants;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.dto.ChangePassword;
import com.yuuu.domain.entity.User;
import com.yuuu.enums.AppHttpCodeEnum;
import com.yuuu.exception.SystemException;
import com.yuuu.service.UserService;
import com.yuuu.utils.SendEmailUtil;
import com.yuuu.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    @Resource
    private SendEmailUtil sendEmailUtil;

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
    private Random random = new Random();
    @PostMapping("/sendVerificationCode")
    public ResponseResult sendVerificationCode(@RequestBody Map<String, String> data) {//这是后端写好的接口
        String nemail = data.get("nemail");
        String verificationCode = String.format("%06d", this.random.nextInt(999999));
        redisCache.setCacheObject(SystemConstants.MAIL_KEY_0 + verificationCode,verificationCode,600, TimeUnit.SECONDS);
        String subject = "邂逅相遇，适我愿兮。阁下接住~~~";
        String content = "【YuuuBlog】验证码: " + verificationCode + "，10分钟内有效。切勿将验证码提供给他人！";
        sendEmailUtil.sendMessage(nemail, subject, content);
        return ResponseResult.okResult("验证码已发送，请耐心等待");
    }

    @PostMapping("/sendCodeForUpdatePassword")
    public ResponseResult sendCodeForUpdatePassword(@RequestBody Map<String, String> data) {//这是后端写好的接口
        String emailPassword = data.get("emailPassword");
        String verificationCode = String.format("%06d", this.random.nextInt(999999));
        redisCache.setCacheObject(SystemConstants.MAIL_KEY_1 + verificationCode,verificationCode,600, TimeUnit.SECONDS);
        String subject = "验证码来咯~~~";
        String content = "【YuuuBlog】验证码: " + verificationCode + "，10分钟内有效。切勿将验证码提供给他人！\\n高山仰止，景行行止，虽不能至，心向往之。";
        sendEmailUtil.sendMessage(emailPassword, subject, content);
        return ResponseResult.okResult("验证码已发送，请耐心等待");
    }

    @PostMapping("/updatePassword")
    public ResponseResult updatePassword(@RequestBody ChangePassword user){

        return userService.updatePassword(user);
    }

    @PostMapping("/oauth/github")
    public ResponseResult oauth(@RequestBody ChangePassword user,@RequestParam String code){
        return userService.oauthGithub(user,code);
    }

    @PostMapping ("/oauth")
    public ResponseResult oauth(@RequestParam String code,@RequestParam String state){
        return userService.oauth(code);
    }




}
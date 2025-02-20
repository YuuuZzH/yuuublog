package com.yuuu.utils;

import cn.hutool.core.date.DateTime;
import com.yuuu.enums.AppHttpCodeEnum;
import com.yuuu.exception.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * 发送邮件工具类
 * @author yuuu
 */
@Component
public class SendEmailUtil {

    private static final String FROM_EMAIL = "***REMOVED***";
    private final JavaMailSender mailSender;

    @Autowired
    public SendEmailUtil(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMessage(String toEmail, String subject, String content) {
        SimpleMailMessage message = getMessage(toEmail, subject, content);
        System.out.println(message);
        try {
            mailSender.send(message);
        } catch (Exception e) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_ERROR);
        }
    }

    private SimpleMailMessage getMessage(String toEmail, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM_EMAIL);
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setSentDate(new DateTime(Instant.now()));
        message.setText(content);
        return message;
    }

}

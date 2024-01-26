package com.yuuu.controller;

import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.dto.UserLoginDto;
import com.yuuu.domain.entity.User;
import com.yuuu.enums.AppHttpCodeEnum;
import com.yuuu.exception.SystemException;
import com.yuuu.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @BelongsProject:YuuuBlog
 * @BelongsPackage:com.yuuu.controller
 * @Author:Yuuu。
 * @CreateTime:2023-03-04 09:45
 * @Description:
 * @Version:1.0
 */
@RestController
public class BlogLoginController {

    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody UserLoginDto user, HttpServletRequest request, Device device) {
        if (!StringUtils.hasText(user.getUserName())) {
            //提示用户输入用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }

    @PostMapping("/logout")
    public ResponseResult logout() {
        return blogLoginService.logout();
    }

}

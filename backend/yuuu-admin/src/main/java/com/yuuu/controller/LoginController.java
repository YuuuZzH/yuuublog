package com.yuuu.controller;

import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.entity.LoginUser;
import com.yuuu.domain.entity.Menu;
import com.yuuu.domain.entity.User;
import com.yuuu.domain.vo.AdminUserInfoVo;
import com.yuuu.domain.vo.RoutersVo;
import com.yuuu.domain.vo.UserInfoVo;
import com.yuuu.enums.AppHttpCodeEnum;
import com.yuuu.exception.SystemException;
import com.yuuu.service.LoginService;
import com.yuuu.service.MenuService;
import com.yuuu.service.RoleService;
import com.yuuu.utils.BeanCopyUtils;
import com.yuuu.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @BelongsProject:YuuuBlog
 * @BelongsPackage:com.yuuu.controller
 * @Author:Yuuu。
 * @CreateTime:2023-03-08 16:03
 * @Description:
 * @Version:1.0
 */
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user, HttpServletRequest request){
        if (!StringUtils.hasText(user.getUserName())){
            //提示用户输入用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }
    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }


    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        return ResponseResult.okResult(loginService.getInfo());
    }

    @GetMapping("/getRouters")
    public ResponseResult<RoutersVo> getRouters(){
        return ResponseResult.okResult(loginService.getRouters());
    }
}

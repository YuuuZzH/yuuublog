package com.yuuu.service.impl;

import com.yuuu.service.LoginService;
import com.yuuu.constants.SystemConstants;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.entity.LoginUser;
import com.yuuu.domain.entity.User;
import com.yuuu.utils.JwtUtil;
import com.yuuu.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @BelongsProject:YuuuBlog
 * @BelongsPackage:com.yuuu.service.impl
 * @Author:Yuuu。
 * @CreateTime:2023-03-04 09:50
 * @Description:
 * @Version:1.0
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否通过认证,密码校验上面的authenticate帮你完成了
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误!");
        }
        //认证通过，用户名和密码都正确
        //获取userId，生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        redisCache.setCacheObject(SystemConstants.LOGIN_KEY + userId,loginUser);
        //把token封装 返回
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        //获取token，解析userId
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //获取userId
        Long id = loginUser.getUser().getId();
        //删除redis中的信息
        redisCache.deleteObject(SystemConstants.LOGIN_KEY + id);
        return ResponseResult.okResult();
    }
}

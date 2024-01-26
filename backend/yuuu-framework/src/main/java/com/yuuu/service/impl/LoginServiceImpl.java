package com.yuuu.service.impl;

import com.yuuu.domain.entity.Menu;
import com.yuuu.domain.vo.AdminUserInfoVo;
import com.yuuu.domain.vo.BlogUserLoginVo;
import com.yuuu.domain.vo.RoutersVo;
import com.yuuu.domain.vo.UserInfoVo;
import com.yuuu.service.LoginService;
import com.yuuu.constants.SystemConstants;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.entity.LoginUser;
import com.yuuu.domain.entity.User;
import com.yuuu.service.MenuService;
import com.yuuu.service.RoleService;
import com.yuuu.utils.BeanCopyUtils;
import com.yuuu.utils.JwtUtil;
import com.yuuu.utils.RedisCache;
import com.yuuu.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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
    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

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
        Long userId = loginUser.getUser().getId();
        List<String> perms = menuService.selectPermsByUserId(userId);
        loginUser.setPermissions(perms);
        String jwt = JwtUtil.createJWT(userId.toString());
        //把用户信息存入redis
        redisCache.setCacheObject(SystemConstants.LOGIN_KEY + userId,loginUser);
        //把token封装 返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(jwt, userInfoVo);
        return ResponseResult.okResult(blogUserLoginVo);
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

    @Override
    public AdminUserInfoVo getInfo() {
        //获取当前登录用户的id
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //查询用户权限和角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());

        //获取用户信息
        User user = loginUser.getUser();
        UserInfoVo vo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        //封装返回
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms,roleKeyList,vo);
        return adminUserInfoVo;
    }

    @Override
    public RoutersVo getRouters() {
        Long userId = SecurityUtils.getUserId();
        //查询menu 结果是tree的形式
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        //封装数据返回
        return new RoutersVo(menus);
    }
}

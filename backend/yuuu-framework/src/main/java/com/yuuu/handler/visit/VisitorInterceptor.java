package com.yuuu.handler.visit;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yuuu.domain.entity.LoginConfig;
import com.yuuu.domain.entity.LoginUser;
import com.yuuu.domain.entity.User;
import com.yuuu.service.LoginConfigService;
import com.yuuu.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author: yuuu
 * Description: 获取访客信息
 */
public class VisitorInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginConfigService loginConfigService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddress = IPUtil.getIpAddr(request);
        String cityInfo = IpToAddressUtil.getCityInfo(ipAddress);
        String browserInfo = UserAgentUtils.getBrowserInfo(request);
        String osInfo = UserAgentUtils.getOperatingSystemInfo(request);
        String devName = UserAgentUtils.getDevName(request);
        LoginConfig loginConfig = new LoginConfig();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof LoginUser) {
            // 登录成功的
            User user = SecurityUtils.getLoginUser().getUser();
            Map<String, String> map = new HashMap<>();
            map.put("userId", String.valueOf(user.getId()));
            map.put("userName", user.getUserName());
            map.put("nickName", user.getNickName());
            map.put("email", user.getEmail());
            map.put("ip", ipAddress);
            map.put("browserInfo", browserInfo);
            map.put("osInfo", osInfo);
            map.put("address", cityInfo);
            map.put("dev", devName);
            saveOrUpdateLoginInfo(map);
        } else {
            LambdaQueryWrapper<LoginConfig> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(LoginConfig::getIp, ipAddress).eq(LoginConfig::getType, LoginConfig.VISITOR);
            queryWrapper.orderByDesc(LoginConfig::getId);
            List<LoginConfig> list = loginConfigService.list(queryWrapper);
            LoginConfig one = null;
            if (list != null && list.size() > 0){
                one = list.get(0);
            }
            if (one != null) {
                if (principal instanceof LoginUser) {
                    return true;
                }
                // 当前ip下已经有访客信息,更新
                one.setDevName(devName);
                one.setLoginTime(new Date());
                one.setBrowser(browserInfo);
                one.setOs(osInfo);
                loginConfigService.updateById(one);
            } else {
                // 新增
                loginConfig.setType(LoginConfig.VISITOR);
                loginConfig.setNickName(VisitorUtil.generateGuestUsername());
                loginConfig.setIp(ipAddress);
                loginConfig.setDevName(devName);
                loginConfig.setLoginTime(new Date());
                loginConfig.setBrowser(browserInfo);
                loginConfig.setOs(osInfo);
                loginConfig.setAddress(cityInfo);
                loginConfigService.save(loginConfig);
            }
        }
        return true;
    }

    private void saveOrUpdateLoginInfo(Map<String, String> map) {
        Long userId = Long.valueOf(map.get("userId"));
        LoginConfig loginConfig = new LoginConfig();
        loginConfig.setUserId(userId);
        loginConfig.setType(LoginConfig.LOGIN_USER);
        loginConfig.setLoginTime(new Date());
        loginConfig.setIp(map.get("ip"));
        loginConfig.setOs(map.get("osInfo"));
        loginConfig.setBrowser(map.get("browserInfo"));
        loginConfig.setDevName(map.get("dev"));
        loginConfig.setAddress(map.get("address"));
        loginConfig.setUserName(map.get("userName"));
        loginConfig.setNickName(map.get("nickName"));
        loginConfig.setEmail(map.get("email"));
        LambdaQueryWrapper<LoginConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LoginConfig::getUserId, userId);
        loginConfigService.saveOrUpdate(loginConfig, queryWrapper);
    }
}

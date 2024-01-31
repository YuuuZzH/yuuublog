package com.yuuu.aspect;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yuuu.domain.entity.LoginConfig;
import com.yuuu.domain.entity.User;
import com.yuuu.domain.vo.BlogUserLoginVo;
import com.yuuu.enums.AppHttpCodeEnum;
import com.yuuu.exception.SystemException;
import com.yuuu.service.LoginConfigService;
import com.yuuu.service.UserService;
import com.yuuu.utils.IPUtil;
import com.yuuu.utils.IpToAddressUtil;
import com.yuuu.utils.SecurityUtils;
import com.yuuu.utils.UserAgentUtils;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 获取用户登录信息的切面类
 * @author: yuuu
 **/
// @Aspect
@Component
@Slf4j
// @Order(Ordered.LOWEST_PRECEDENCE - 1)
public class LoginInfoAspect {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginConfigService loginConfigService;

    /**
     * @author yuuu
     * @description 在切面类中，第一次 记录用户登录信息时，用户还未登录成功，或者说 请求还未携带 token ，因此 SpringSecurity 还不能获取到LoginUser
     * 此时，通过  SecurityUtils.getLoginUser().getUser(); 会报错
     * 【java.lang.ClassCastException: java.lang.String cannot be cast to com.yuuu.domain.entity.LoginUser】
     * 因为 getAuthentication().getPrincipal() 是 anonymousUser
     * 解决：通过切 返回的结果，使用反射获取到响应对象，避免回表
     */
    // @AfterReturning(pointcut = "execution(* com.yuuu.controller.*Controller.*login(..))", returning = "result")
    public void afterLogin(JoinPoint joinPoint, Object result) {
        // 通过反射获取 Response的data数据
        BlogUserLoginVo blogUserLoginVo;
        Class<?> aClass = result.getClass();
        try {
            Field dataField = aClass.getDeclaredField("data");
            dataField.setAccessible(true);
            blogUserLoginVo = (BlogUserLoginVo) dataField.get(result);
        } catch (Exception e) {
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        Long userId = blogUserLoginVo.getUserInfo().getId();
        Object[] args = joinPoint.getArgs();
        Map<String, String> map = new HashMap<>();
        map.put("userId", String.valueOf(userId));
        map.put("userName", blogUserLoginVo.getUserInfo().getUserName());
        map.put("nickName", blogUserLoginVo.getUserInfo().getNickName());
        map.put("email", blogUserLoginVo.getUserInfo().getEmail());
        String devName = "其他";
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                HttpServletRequest request = (HttpServletRequest) arg;
                String ipAddress = IPUtil.getIpAddr(request);
                String cityInfo = IpToAddressUtil.getCityInfo(ipAddress);
                if (StrUtil.isBlank(cityInfo)) {
                    cityInfo = "暂无位置信息";
                }
                map.put("ip", ipAddress);
                map.put("address", cityInfo);
                String browserInfo = UserAgentUtils.getBrowserInfo(request);
                String osInfo = UserAgentUtils.getOperatingSystemInfo(request);
                map.put("browserInfo", browserInfo);
                map.put("osInfo", osInfo);
                String userAgent = request.getHeader("User-Agent");
                UserAgent parsedAgent = UserAgent.parseUserAgentString(userAgent);
                OperatingSystem os = parsedAgent.getOperatingSystem();
                DeviceType deviceType = os.getDeviceType();
                devName = String.valueOf(deviceType);
            }
        }
        map.put("dev", devName);
        // saveOrUpdateLoginInfo(map);
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

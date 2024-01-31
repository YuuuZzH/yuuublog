package com.yuuu.handler.visit;

import com.yuuu.domain.entity.LoginConfig;
import com.yuuu.domain.entity.LoginUser;
import com.yuuu.service.LoginConfigService;
import com.yuuu.utils.IPUtil;
import com.yuuu.utils.IpToAddressUtil;
import com.yuuu.utils.UserAgentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        String requestURI = request.getRequestURI();
        System.out.println("执行拦截器："+requestURI);
        return true;
    }
}

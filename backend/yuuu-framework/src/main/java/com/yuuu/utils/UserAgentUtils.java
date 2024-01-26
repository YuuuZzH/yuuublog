package com.yuuu.utils;

import cn.hutool.core.util.StrUtil;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;

import javax.servlet.http.HttpServletRequest;

public class UserAgentUtils {

    public static String getBrowserInfo(HttpServletRequest request) {
        String userAgentString = request.getHeader("User-Agent");
        if (StrUtil.isBlank(userAgentString)){
            return "其他";
        }
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);
        // 获取浏览器信息
        String browserName = userAgent.getBrowser().getName();
        // Version browserVersion = userAgent.getBrowserVersion();
        // String browserVersionString = (browserVersion != null) ? browserVersion.getVersion() : "";

        return browserName;
    }

    public static String getOperatingSystemInfo(HttpServletRequest request) {
        String userAgentString = request.getHeader("User-Agent");
        if (StrUtil.isBlank(userAgentString)){
            return "其他";
        }
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);
        // 获取操作系统信息
        return userAgent.getOperatingSystem().getName();
    }

    public static void main(String[] args) {
        // 在实际使用中，替换下面的字符串为具体的用户代理信息
        String userAgentString = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.0.0 Safari/537.36";

        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);

        // 获取浏览器信息
        String browserName = userAgent.getBrowser().getName();
        Version browserVersion = userAgent.getBrowserVersion();
        String browserVersionString = (browserVersion != null) ? browserVersion.getVersion() : "";
        System.out.println("Browser: " + browserName + " " + browserVersionString);

        // 获取操作系统信息
        String operatingSystem = userAgent.getOperatingSystem().getName();
        System.out.println("Operating System: " + operatingSystem);
    }
}

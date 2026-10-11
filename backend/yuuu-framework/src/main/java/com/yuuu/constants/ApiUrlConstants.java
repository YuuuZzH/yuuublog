package com.yuuu.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;

/**
 * api接口路径常量
 * @author yuuu
 */
@AllArgsConstructor
@Getter
public enum ApiUrlConstants {

    GITHUB_ACCESS_TOKEN("https://github.com/login/oauth/access_token", "github获取token", HttpMethod.POST),
    GITHUB_USER("https://api.github.com/user", "github获取用户信息", HttpMethod.GET),
    DIFY("https://api.dify.ai/v1/", "dify根域名", null),
    DIFY_CHAT("chat-messages", "聊天接口", HttpMethod.POST),
    ;


    private final String url;
    private final String desc;

    private final HttpMethod method;
}

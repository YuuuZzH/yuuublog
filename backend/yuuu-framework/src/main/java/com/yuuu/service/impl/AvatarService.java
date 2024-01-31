package com.yuuu.service.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class AvatarService {
    private static final String API_URL = "https://img.xjh.me/random_img.php?return=json";
    private final RestTemplate restTemplate;

    public AvatarService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getRandomAvatar() {
        String avatar = "";
        ResponseEntity<String> response = restTemplate.getForEntity(API_URL, String.class);
        if (response.getStatusCodeValue() == 200){
            String body = response.getBody();
            Map map = JSON.parseObject(body, Map.class);
            avatar = (String) map.get("img");
        }
        return avatar;
    }
}

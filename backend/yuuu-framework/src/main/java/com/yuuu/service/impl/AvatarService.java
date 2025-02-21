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
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(API_URL, String.class);
            if (response.getStatusCodeValue() == 200){
                String body = response.getBody();
                Map map = JSON.parseObject(body, Map.class);
                avatar = (String) map.get("img");
            }
        }catch (Exception ignore){
            avatar = "http://qiniu.yuuu.online/2023/08/11/8dbfd3b51126440ca7a2b239cc628b5c.jpg";
        }
        return avatar;
    }
}

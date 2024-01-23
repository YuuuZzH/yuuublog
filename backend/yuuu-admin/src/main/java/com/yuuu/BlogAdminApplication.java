package com.yuuu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @BelongsProject:YuuuBlog
 * @BelongsPackage:com.yuuu
 * @Author:Yuuu。
 * @CreateTime:2023-03-08 14:53
 * @Description:
 * @Version:1.0
 */
@SpringBootApplication
@MapperScan("com.yuuu.mapper")
public class BlogAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogAdminApplication.class,args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

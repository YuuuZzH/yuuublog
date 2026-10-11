package com.yuuu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @BelongsProject:YuuuBlog
 * @BelongsPackage:com.yuuu
 * @Author:Yuuu。
 * @CreateTime:2023-03-02 14:16
 * @Description:
 * @Version:1.0
 */
@SpringBootApplication
@ConfigurationPropertiesScan
@EnableScheduling
@EnableSwagger2
public class YuuuBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(YuuuBlogApplication.class,args);
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

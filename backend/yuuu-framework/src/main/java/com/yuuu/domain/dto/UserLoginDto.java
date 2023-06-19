package com.yuuu.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: yuuu
 * Description:
 * Email: ***REMOVED***
 * CreatedTime: 2023-06-02 15:40:15:40
 */

@Data
public class UserLoginDto {

    //用户名
    private String userName;
    //密码
    private String password;
    //验证码
    private String captcha;
}

package com.yuuu.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: yuuu
 * Description:
 * Email: 864326354@qq.com
 * CreatedTime: 2023-07-08 09:56:9:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassword {

    //邮箱
    private String emailPassword;
    //密码
    private String confirmNewPassword;
    //验证码
    private String emailCaptchaUpdate;

}

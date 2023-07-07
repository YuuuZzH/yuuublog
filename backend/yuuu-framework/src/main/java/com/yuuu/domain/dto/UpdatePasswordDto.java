package com.yuuu.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: yuuu
 * Description: 修改密码
 * Email: ***REMOVED***
 * CreatedTime: 2023-07-04 19:45:19:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordDto {
    //邮箱
    private String emailPassword;
    //密码
    private String confirmNewPassword;
    //验证码
    private String emailCaptchaUpdate;
}

package com.yuuu.domain.vo;

import com.yuuu.domain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject:YuuuBlog
 * @BelongsPackage:com.yuuu.domain.vo
 * @Author:Yuuu。
 * @CreateTime:2023-03-19 14:57
 * @Description:
 * @Version:1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUpdateVo {
    private Long id;

    //用户名
    private String userName;
    //昵称
    private String nickName;

    //账号状态（0正常 1停用）
    private String status;
    //邮箱
    private String email;

    //用户性别（0男，1女，2未知）
    private String sex;
    private List<Long> roleIds;

}

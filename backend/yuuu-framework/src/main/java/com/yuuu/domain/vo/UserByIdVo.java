package com.yuuu.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @BelongsProject:YuuuBlog
 * @BelongsPackage:com.yuuu.domain.vo
 * @Author:Yuuu。
 * @CreateTime:2023-03-19 14:53
 * @Description:
 * @Version:1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserByIdVo {
    private List<Long> roleIds;
    private List<RoleAllVo> roles;
    private UserUpdateVo user;
}

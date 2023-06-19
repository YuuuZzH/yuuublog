package com.yuuu.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongsProject:YuuuBlog
 * @BelongsPackage:com.yuuu.domain.dto
 * @Author:Yuuu。
 * @CreateTime:2023-03-17 16:14
 * @Description:
 * @Version:1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleListDto {
    //角色名称
    private String roleName;
    //角色状态（0正常 1停用）
    private String status;
}

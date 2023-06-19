package com.yuuu.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @BelongsProject:YuuuBlog
 * @BelongsPackage:com.yuuu.domain.dto
 * @Author:Yuuu。
 * @CreateTime:2023-03-17 21:46
 * @Description:
 * @Version:1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRoleDto {
    //角色名称
    private String roleName;
    //角色权限字符串
    private String roleKey;
    //显示顺序
    private Integer roleSort;
    //角色状态（0正常 1停用）
    private String status;
    //备注
    private String remark;
    private List<Long> menuIds;
}

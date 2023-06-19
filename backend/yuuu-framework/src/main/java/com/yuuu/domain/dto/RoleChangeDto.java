package com.yuuu.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongsProject:YuuuBlog
 * @BelongsPackage:com.yuuu.domain.dto
 * @Author:Yuuu。
 * @CreateTime:2023-03-17 21:28
 * @Description:
 * @Version:1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleChangeDto {
    private Long roleId;
    private String status;
}

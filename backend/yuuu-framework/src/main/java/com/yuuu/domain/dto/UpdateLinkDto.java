package com.yuuu.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongsProject:YuuuBlog
 * @BelongsPackage:com.yuuu.domain.dto
 * @Author:Yuuu。
 * @CreateTime:2023-03-16 20:49
 * @Description:
 * @Version:1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateLinkDto {
    private Long id;
    private String name;

    private String logo;

    private String description;
    //网站地址
    private String address;
    private String status;
}

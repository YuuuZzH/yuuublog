package com.yuuu.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @BelongsProject:YuuuBlog
 * @BelongsPackage:com.yuuu.domain.vo
 * @Author:Yuuu。
 * @CreateTime:2023-03-03 14:49
 * @Description:
 * @Version:1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVo {
    private List rows;
    private Long total;
}

package com.yuuu.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @BelongsProject:YuuuBlog
 * @BelongsPackage:com.yuuu.domain.vo
 * @Author:Yuuu。
 * @CreateTime:2023-03-17 21:40
 * @Description:
 * @Version:1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeVo {
    private Long id;
    private String label;
    private Long parentId;
    private List<TreeVo> children;
}

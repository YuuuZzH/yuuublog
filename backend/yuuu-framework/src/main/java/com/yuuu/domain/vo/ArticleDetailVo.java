package com.yuuu.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @BelongsProject:YuuuBlog
 * @BelongsPackage:com.yuuu.domain.vo
 * @Author:Yuuu。
 * @CreateTime:2023-03-03 18:28
 * @Description:
 * @Version:1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailVo {
    @TableId
    private Long id;
    //标题
    private String title;
    //分类id
    private Long categoryId;
    //所属分类名
    private String categoryName;
    //文章摘要
    private String summary;
    //缩略图
    private String thumbnail;
    //内容
    private String content;
    //访问量
    private Long viewCount;

    private Date createTime;
}

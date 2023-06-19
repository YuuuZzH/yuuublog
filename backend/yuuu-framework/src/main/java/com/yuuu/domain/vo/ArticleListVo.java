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
 * @CreateTime:2023-03-03 14:43
 * @Description:
 * @Version:1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleListVo {
    @TableId
    private Long id;
    //标题
    private String title;
    //文章内容
    private String content;
    //所属分类名
    private String categoryName;
    //文章摘要
    private String summary;
    //是否置顶（0否，1是）
    private String isTop;
    //状态（0已发布，1草稿）
    private String status;
    //访问量
    private Long viewCount;
    //缩略图
    private String thumbnail;
    private Date createTime;
}

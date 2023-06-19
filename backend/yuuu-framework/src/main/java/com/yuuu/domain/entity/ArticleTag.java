package com.yuuu.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 文章标签关联表(ArticleTag)表实体类
 *
 * @author makejava
 * @since 2023-03-16 21:24:23
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("yuuu_article_tag")
public class ArticleTag  {
    private static final long serialVersionUID = 625337492348897098L;
    //文章id
    @TableId
    private Long articleId;
    //标签id@TableId
    private Long tagId;




}

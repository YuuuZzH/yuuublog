package com.yuuu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuuu.domain.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @BelongsProject:YuuuBlog
 * @BelongsPackage:com.yuuu.mapper
 * @Author:Yuuu。
 * @CreateTime:2023-03-02 14:45
 * @Description:
 * @Version:1.0
 */
public interface ArticleMapper extends BaseMapper<Article> {
    void deleteArticleTagById(Long id);

    void insertArticleTag(@Param("articleId") Long id,@Param("tags") List<Long> tags);

    List<String> getArticleByInput(String inputValue);
}

package com.yuuu.runner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yuuu.constants.SystemConstants;
import com.yuuu.domain.entity.Article;
import com.yuuu.mapper.ArticleMapper;
import com.yuuu.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @BelongsProject:YuuuBlog
 * @BelongsPackage:com.yuuu.runner
 * @Author:Yuuu。
 * @CreateTime:2023-03-07 18:33
 * @Description:
 * @Version:1.0
 */
@Component
public class ViewCountRunner implements CommandLineRunner{

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;
    @Override
    public void run(String... args) throws Exception {
        //查询博客信息和浏览量
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        Map<String, Integer> viewMap = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(),
                        article -> article.getViewCount().intValue()));
        //存入redis中
        redisCache.setCacheMap(SystemConstants.VIEW_COUNT,viewMap);
    }
}

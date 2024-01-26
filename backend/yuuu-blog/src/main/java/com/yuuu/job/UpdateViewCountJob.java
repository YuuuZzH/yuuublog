package com.yuuu.job;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yuuu.constants.SystemConstants;
import com.yuuu.domain.entity.Article;
import com.yuuu.service.ArticleService;
import com.yuuu.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @BelongsProject:YuuuBlog
 * @BelongsPackage:com.yuuu.job
 * @Author:Yuuu。
 * @CreateTime:2023-03-07 19:20
 * @Description:
 * @Version:1.0
 */
@Component
public class UpdateViewCountJob {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;


    // @Scheduled(cron = "0/5 * * * * ?")
    public void updateViewCountJob() {
        //获取redis中的浏览量
        Map<String, Integer> cacheMap = redisCache.getCacheMap(SystemConstants.VIEW_COUNT);
        List<Article> articles = cacheMap.entrySet()
                .stream()
                .map(stringIntegerEntry -> {
                    Long key = Long.valueOf(stringIntegerEntry.getKey());
                    Long value = Long.valueOf(stringIntegerEntry.getValue());
                    return new Article(key, value);
                }).collect(Collectors.toList());
        for (Article article : articles) {
            LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Article :: getId, article.getId());
            updateWrapper.set(Article :: getViewCount, article.getViewCount());
            articleService.update(updateWrapper);
        }
    }
}

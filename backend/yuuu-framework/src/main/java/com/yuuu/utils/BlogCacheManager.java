package com.yuuu.utils;

import com.yuuu.constants.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * 前台热点缓存管理：读写缓存 + 后台写库后的失效
 *
 * 失效放在 framework 层（前台与后台共用同一份 Service 实现），
 * 后台进程写库后直接删除 Redis 中的缓存 key，前台进程下一次读即回源。
 * 前提：前台(yuuu-blog)与后台(yuuu-admin)必须使用同一个 Redis database。
 */
@Component
public class BlogCacheManager {

    @Autowired
    private RedisCache redisCache;

    /**
     * 读缓存，类型不匹配时当作未命中（并清理脏数据），避免 Fastjson 反序列化类型漂移导致强转异常
     */
    public <T> T get(String key, Class<T> type) {
        Object cache = redisCache.getCacheObject(key);
        if (cache == null) {
            return null;
        }
        if (!type.isInstance(cache)) {
            redisCache.deleteObject(key);
            return null;
        }
        return type.cast(cache);
    }

    /**
     * 写缓存，统一带 TTL，避免无过期时间的脏数据长期残留
     */
    public void set(String key, Object value, Integer minutes) {
        if (value == null || minutes == null) {
            return;
        }
        redisCache.setCacheObject(key, value, minutes, TimeUnit.MINUTES);
    }

    /**
     * 文章新增/修改/删除后失效：详情、热门榜、所有分页列表
     */
    public void evictArticle(Long articleId) {
        if (articleId != null) {
            redisCache.deleteObject(SystemConstants.ARTICLE_DETAIL_KEY + articleId);
        }
        redisCache.deleteObject(SystemConstants.HOT_ARTICLE_KEY);
        deleteByPrefix(SystemConstants.ARTICLE_LIST_KEY);
    }

    /**
     * 分类新增/修改/删除/改状态后失效：分类列表 + 文章列表（列表返回体含 categoryName）
     */
    public void evictCategory() {
        redisCache.deleteObject(SystemConstants.CATEGORY_LIST_KEY);
        deleteByPrefix(SystemConstants.ARTICLE_LIST_KEY);
    }

    /**
     * 友链新增/修改/删除后失效
     */
    public void evictLink() {
        redisCache.deleteObject(SystemConstants.LINK_LIST_KEY);
    }

    /**
     * 关于我保存后失效
     */
    public void evictAbout() {
        redisCache.deleteObject(SystemConstants.ABOUT_KEY);
    }

    /**
     * 按前缀批量删除（文章分页 key 数量有限，可接受一次 KEYS 扫描）
     */
    private void deleteByPrefix(String prefix) {
        Collection<String> keys = redisCache.keys(prefix + "*");
        if (keys != null && !keys.isEmpty()) {
            redisCache.deleteObject(keys);
        }
    }
}

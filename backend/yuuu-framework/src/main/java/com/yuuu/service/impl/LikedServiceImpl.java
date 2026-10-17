package com.yuuu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.entity.Article;
import com.yuuu.domain.entity.Comment;
import com.yuuu.domain.entity.Liked;
import com.yuuu.enums.AppHttpCodeEnum;
import com.yuuu.exception.SystemException;
import com.yuuu.mapper.LikedMapper;
import com.yuuu.service.ArticleService;
import com.yuuu.service.CommentService;
import com.yuuu.service.LikedService;
import com.yuuu.utils.BlogCacheManager;
import com.yuuu.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * (Liked)表服务实现类
 *
 * @author makejava
 * @since 2023-08-17 11:47:21
 */
@Service("likedService")
public class LikedServiceImpl extends ServiceImpl<LikedMapper, Liked> implements LikedService {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogCacheManager blogCacheManager;

    @Override
    public ResponseResult articleLike(Long id) {
        Article article = articleService.getById(id);
        if (Objects.isNull(article)) {
            return ResponseResult.errorResult(801, "没有该文章，请重试");
        }
        Long userId = SecurityUtils.getUserId();
        LambdaQueryWrapper<Liked> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Liked::getArticleId, id).eq(Liked::getUserId, userId);
        List<Liked> list = list(queryWrapper);
        if (list.size() < 1) {
            //第一次点赞,直接插入数据
            Liked liked = new Liked();
            liked.setArticleId(id);
            liked.setUserId(userId);
            liked.setIsLikeA(1);
            boolean save = save(liked);
            article.setLikes(article.getLikes() + 1);
            articleService.updateById(article);
            // 点赞数变化：失效这篇文章的详情缓存，否则前台点赞数会回退
            blogCacheManager.evictArticle(id);
            if (save) {
                return ResponseResult.okResult();
            } else {
                return ResponseResult.errorResult(802, "网络好像波动啦~");
            }
        } else {
            //第二次进来，可能是点赞也可能是取消点赞
            Liked liked = list.get(0);
            if (liked.getIsLikeA() == 1) {//取消点赞
                liked.setIsLikeA(0);
                updateById(liked);
                article.setLikes(article.getLikes() - 1);
                articleService.updateById(article);
            // 点赞数变化：失效这篇文章的详情缓存，否则前台点赞数会回退
            blogCacheManager.evictArticle(id);
            } else {//点赞
                liked.setIsLikeA(1);
                updateById(liked);
                article.setLikes(article.getLikes() + 1);
                articleService.updateById(article);
            // 点赞数变化：失效这篇文章的详情缓存，否则前台点赞数会回退
            blogCacheManager.evictArticle(id);
            }
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult commentLike(Long id) {
        Comment comment = commentService.getById(id);
        if (Objects.isNull(comment)) {
            return ResponseResult.errorResult(803, "没有该评论，请重试");
        }
        Long userId = SecurityUtils.getUserId();
        LambdaQueryWrapper<Liked> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Liked::getCommentId, id).eq(Liked::getUserId, userId);
        List<Liked> list = list(queryWrapper);
        if (list.size() < 1) {
            //第一次点赞,直接插入数据
            Liked liked = new Liked();
            liked.setCommentId(id);
            liked.setUserId(userId);
            liked.setIsLikeC(1);
            boolean save = save(liked);
            comment.setLikes(comment.getLikes() + 1);
            commentService.updateById(comment);
            if (save) {
                return ResponseResult.okResult();
            } else {
                return ResponseResult.errorResult(802, "网络好像波动啦~");
            }
        } else {
            //第二次进来，可能是点赞也可能是取消点赞
            Liked liked = list.get(0);
            if (liked.getIsLikeC() == 1) {//取消点赞
                liked.setIsLikeC(0);
                updateById(liked);
                comment.setLikes(comment.getLikes() - 1);
                commentService.updateById(comment);
            } else {//点赞
                liked.setIsLikeC(1);
                updateById(liked);
                comment.setLikes(comment.getLikes() + 1);
                commentService.updateById(comment);
            }
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getArticleLike(Long id) {
        Long userId = null;
        try {
            userId = SecurityUtils.getUserId();
        } catch (Exception e) {
            throw new SystemException(AppHttpCodeEnum.NEED_LOGIN);
        }
        if (userId == null) {
            // 用户未登录，根据你的需求设置默认值
            userId = -1L; //
        }
        LambdaQueryWrapper<Liked> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Liked::getArticleId, id).eq(Liked::getUserId, userId);
        Liked liked = getOne(queryWrapper);
        if (Objects.isNull(liked)) {
            return ResponseResult.okResult();
        }
        return ResponseResult.okResult(liked.getIsLikeA());
    }

    @Override
    public ResponseResult getCommentLike(Long[] ids) {
        Long userId = null;
        try {
            userId = SecurityUtils.getUserId();
        } catch (Exception e) {
            throw new SystemException(AppHttpCodeEnum.NEED_LOGIN);
        }
        if (userId == null) {
            // 用户未登录，根据你的需求设置默认值
            userId = -1L; //
        }
        List<Long> commentIds = new ArrayList<>();
        for (Long id : ids) {
            LambdaQueryWrapper<Liked> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Liked::getUserId, userId).eq(Liked::getCommentId, id);
            Liked liked = getOne(queryWrapper);
            if (Objects.isNull(liked)) {
                ResponseResult.okResult();
            }else{
                commentIds.add(liked.getCommentId());
            }
        }
        return ResponseResult.okResult(commentIds);
    }


}

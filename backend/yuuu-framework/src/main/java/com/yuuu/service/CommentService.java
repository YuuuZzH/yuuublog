package com.yuuu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-03-04 17:15:52
 */
public interface CommentService extends IService<Comment> {

    ResponseResult getCommentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);


}


package com.yuuu.controller;

import com.yuuu.constants.SystemConstants;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.entity.Comment;
import com.yuuu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @BelongsProject:YuuuBlog
 * @BelongsPackage:com.yuuu.controller
 * @Author:Yuuu。
 * @CreateTime:2023-03-04 18:35
 * @Description:
 * @Version:1.0
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @GetMapping("/commentList")
    public ResponseResult getCommentList(Long articleId, Integer pageNum, Integer pageSize){
        return commentService.getCommentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }

    @PostMapping
    public ResponseResult addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }

    @GetMapping("/linkCommentList")
    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize){
        return commentService.getCommentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
    }

    @GetMapping("/likes/{id}")
    public ResponseResult getLikes(@PathVariable("id") Long id){
        return commentService.getLikes(id);
    }
}

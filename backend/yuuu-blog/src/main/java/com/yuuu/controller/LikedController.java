package com.yuuu.controller;

import com.yuuu.domain.ResponseResult;

import com.yuuu.service.LikedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author: yuuu
 * Description: 点赞相关
 * Email: 864326354@qq.com
 * CreatedTime: 2023-08-17 10:18:10:18
 */
@RestController
@RequestMapping("/like")
public class LikedController {
    @Autowired
    private LikedService likedService;

    @PostMapping("/article/{id}")
    public ResponseResult articleLike(@PathVariable("id") Long id) {
        return likedService.articleLike(id);
    }

    @PostMapping("/comment/{id}")
    public ResponseResult commentList(@PathVariable("id") Long id) {
        return likedService.commentLike(id);
    }

    @GetMapping("/article/{id}")
    public ResponseResult getArticleLike(@PathVariable("id") Long id){
        return likedService.getArticleLike(id);
    }
   @PostMapping("/comment")
    public ResponseResult getCommentLike(@RequestBody Map<String,Long[]> map){
        Long[] commentIds = map.get("commentIds");
        return likedService.getCommentLike(commentIds);
    }
}

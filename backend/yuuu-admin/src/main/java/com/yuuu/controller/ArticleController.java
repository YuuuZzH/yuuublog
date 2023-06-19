package com.yuuu.controller;

import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.dto.ArticleDto;
import com.yuuu.domain.dto.ArticleListDto;
import com.yuuu.domain.vo.ArticleByIdVo;
import com.yuuu.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @BelongsProject:YuuuBlog
 * @BelongsPackage:com.yuuu.controller
 * @Author:Yuuu。
 * @CreateTime:2023-03-16 21:12
 * @Description:
 * @Version:1.0
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;


    @PostMapping
    public ResponseResult add(@RequestBody ArticleDto articleDto){
        return articleService.add(articleDto);
    }

    @GetMapping("/list")
    public ResponseResult articleList(Integer pageNum, Integer pageSize, ArticleListDto articleListDto){
        return articleService.articleListAdmin(pageNum,pageSize,articleListDto);
    }
    @GetMapping("/{id}")
    public ResponseResult getArticleById(@PathVariable("id") Long id){
        return articleService.getArticleById(id);
    }

    @PutMapping
    public ResponseResult updateArticle(@RequestBody ArticleByIdVo article){
        return articleService.updateArticle(article);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteArticle(@PathVariable("id") Long id){
        return articleService.deleteArticle(id);
    }

}

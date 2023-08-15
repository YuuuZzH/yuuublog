package com.yuuu.controller;

import cn.hutool.core.util.StrUtil;
import com.yuuu.domain.ResponseResult;
import com.yuuu.service.ArticleService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @BelongsProject:YuuuBlog
 * @BelongsPackage:com.yuuu.controller
 * @Author:Yuuu。
 * @CreateTime:2023-03-02 14:53
 * @Description:
 * @Version:1.0
 */
@RestController
@RequestMapping("/article")
@MapperScan("com.yuuu.mapper")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    //查询热门文章
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        return articleService.hotArticleList();
    }

    //查询文章列表
    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    //查询文章详情
    @GetMapping("/{id}")
    public ResponseResult articleDetailById(@PathVariable("id") Long id){
        return articleService.articleDetailById(id);
    }

    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable("id") Long articleId){
        return articleService.updateViewCount(articleId);
    }

    @GetMapping("/getArticleByInput")
    public ResponseResult getArticleByInput(@RequestParam String inputValue){
        if (StrUtil.isBlank(inputValue)){
            return new ResponseResult();
        }
        return articleService.getArticleByInput(inputValue);
    }
    @GetMapping("/search")
    public ResponseResult articleDetailByTitle(@RequestParam String title){
        if (StrUtil.isBlank(title)){
            return ResponseResult.errorResult(614,"文章标题错误");
        }
        return articleService.articleDetailByTitle(title);
    }
}

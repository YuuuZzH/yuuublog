package com.yuuu.controller;

import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.dto.ArticleDto;
import com.yuuu.domain.dto.ArticleListDto;
import com.yuuu.domain.vo.ArticleByIdVo;
import com.yuuu.enums.AppHttpCodeEnum;
import com.yuuu.exception.SystemException;
import com.yuuu.service.ArticleService;
import com.yuuu.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("@ps.hasPermission('content:article:publish')")
    public ResponseResult add(@RequestBody ArticleDto articleDto){
        if (!SecurityUtils.isAdmin()){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return articleService.add(articleDto);
    }

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('content:article:list')")
    public ResponseResult articleList(Integer pageNum, Integer pageSize, ArticleListDto articleListDto){
        return articleService.articleListAdmin(pageNum,pageSize,articleListDto);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('content:article:query')")
    public ResponseResult getArticleById(@PathVariable("id") Long id){
        return articleService.getArticleById(id);
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('content:article:writer')")
    public ResponseResult updateArticle(@RequestBody ArticleByIdVo article){
        if (!SecurityUtils.isAdmin()){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return articleService.updateArticle(article);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('content:article:remove')")
    public ResponseResult deleteArticle(@PathVariable("id") Long id){
        if (!SecurityUtils.isAdmin()){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return articleService.deleteArticle(id);
    }

}

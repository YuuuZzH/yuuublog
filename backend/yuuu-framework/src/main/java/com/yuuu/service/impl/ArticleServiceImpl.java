package com.yuuu.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuuu.constants.SystemConstants;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.dto.ArticleDto;
import com.yuuu.domain.dto.ArticleListDto;
import com.yuuu.domain.entity.Article;
import com.yuuu.domain.entity.ArticleTag;
import com.yuuu.domain.entity.Category;
import com.yuuu.domain.entity.Liked;
import com.yuuu.domain.vo.*;
import com.yuuu.enums.AppHttpCodeEnum;
import com.yuuu.exception.SystemException;
import com.yuuu.mapper.ArticleMapper;
import com.yuuu.mapper.ArticleTagMapper;
import com.yuuu.service.ArticleService;
import com.yuuu.service.ArticleTagService;
import com.yuuu.service.CategoryService;
import com.yuuu.service.LikedService;
import com.yuuu.utils.BeanCopyUtils;
import com.yuuu.utils.RedisCache;
import com.yuuu.utils.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.yuuu.constants.SystemConstants.ARTICLE_STATUS_NORMAL;

/**
 * @BelongsProject:YuuuBlog
 * @BelongsPackage:com.yuuu.service.impl
 * @Author:Yuuu。
 * @CreateTime:2023-03-02 14:47
 * @Description:
 * @Version:1.0
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private LikedService likedService;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章，返回ResponseResult结果
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        //1.正式发布
        wrapper.eq(Article::getStatus, ARTICLE_STATUS_NORMAL);
        //2.按浏览量降序排列
        wrapper.orderByDesc(Article::getViewCount);
        //3.最多查询10条数据
        Page<Article> page = new Page<>(1, 10);
        page(page, wrapper);
        List<Article> articles = page.getRecords();
        //bean复制
        List<HotArticleVo> articleVoList = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return ResponseResult.okResult(articleVoList);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //分类条件
        queryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        //正式发布的文章
        queryWrapper.eq(Article::getStatus, SystemConstants.CATEGORY_STATUS_NORMAL);
        //是否置顶，置顶的按访问量排
        queryWrapper.orderByDesc(Article::getIsTop).orderByDesc(Article::getViewCount);
        Page page = new Page(pageNum, pageSize);
        page(page, queryWrapper);
        //查询categoryName
        List<Article> records = page.getRecords();
        //根据id去查name
        records = records.stream()
                .map(article -> {
                    Category category = categoryService.getById(article.getCategoryId());
                    article.setCategoryName(category.getName());
                    return article;
                })
                .collect(Collectors.toList());
        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(records, ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult articleDetailById(Long id) {
        //查询文章
        Article article = getById(id);
        //从redis中查询viewCount
        Integer viewCount = redisCache.getCacheMapValue(SystemConstants.VIEW_COUNT, id.toString());
        //封装Vo
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        articleDetailVo.setViewCount(viewCount.longValue());
        //查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (category != null) {
            articleDetailVo.setCategoryName(category.getName());
        }
        return ResponseResult.okResult(articleDetailVo);
    }


    @Override
    public ResponseResult updateViewCount(Long articleId) {
        redisCache.incrementCacheMapValue(SystemConstants.VIEW_COUNT, articleId.toString(), 1);
        return ResponseResult.okResult();
    }
    @Override
    @Transactional
    public ResponseResult add(ArticleDto articleDto) {
        if (StrUtil.isBlank(articleDto.getTitle()) || StrUtil.isBlank(articleDto.getContent())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NOT_NULL);
        }
        //添加博客
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        save(article);

        //根据标签id获取文章对应标签集合
        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());

        //添加 博客和标签的关联
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult articleListAdmin(Integer pageNum, Integer pageSize, ArticleListDto articleListDto) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(articleListDto.getTitle())) {
            queryWrapper.like(Article::getTitle, articleListDto.getTitle());
        }
        if (StringUtils.hasText(articleListDto.getSummary())) {
            queryWrapper.like(Article::getSummary, articleListDto.getSummary());
        }
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<Article> records = page.getRecords();
        List<ArticleListAdminVo> articleListAdminVos = BeanCopyUtils.copyBeanList(records, ArticleListAdminVo.class);
        return ResponseResult.okResult(new PageVo(articleListAdminVos, page.getTotal()));
    }

    @Override
    public ResponseResult getArticleById(Long id) {
        Article article = getById(id);
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId, id);
        //根据文章id查询文章标签集合
        List<ArticleTag> list = articleTagService.list(queryWrapper);
        //得到标签id的集合
        List<Long> tags = list.stream().map(ArticleTag::getTagId)
                .collect(Collectors.toList());
        //封装Vo返回
        ArticleByIdVo articleByIdVo = new ArticleByIdVo();
        BeanUtils.copyProperties(article, articleByIdVo);
        articleByIdVo.setTags(tags);
        return ResponseResult.okResult(articleByIdVo);
    }

    @Override
    @Transactional
    public ResponseResult updateArticle(ArticleByIdVo article) {
        Article article1 = BeanCopyUtils.copyBean(article, Article.class);
        updateById(article1);
        //先删除以前的，再建立新的
        List<Long> tags = article.getTags();
        if (Objects.nonNull(tags) && tags.size() > 0) {
            baseMapper.deleteArticleTagById(article.getId());
            baseMapper.insertArticleTag(article.getId(), tags);
        } else {
            baseMapper.deleteArticleTagById(article.getId());
        }
        return ResponseResult.okResult();

    }

    @Override
    public ResponseResult deleteArticle(Long id) {
        boolean b = removeById(id);
        if (b) {
            return ResponseResult.okResult();
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public ResponseResult getArticleByInput(String inputValue) {
        List<ArticleInputVo> list = baseMapper.getArticleByInput(inputValue);
        if (list != null && list.size() > 0){
            return ResponseResult.okResult(list);
        }else{
            return ResponseResult.okResult(Collections.emptyList());
        }
    }

    @Override
    public ResponseResult articleDetailByTitle(String title) {
        LambdaQueryWrapper<Article> qw = new LambdaQueryWrapper<>();
        qw.eq(Article::getTitle,title);
        Article article = getOne(qw);
        if (article == null){
            return ResponseResult.errorResult(616,"您输入的数据有误");
        }
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId, article.getId());
        //根据文章id查询文章标签集合
        List<ArticleTag> list = articleTagService.list(queryWrapper);
        //得到标签id的集合
        List<Long> tags = list.stream().map(ArticleTag::getTagId)
                .collect(Collectors.toList());
        //封装Vo返回
        ArticleByIdVo articleByIdVo = new ArticleByIdVo();
        BeanUtils.copyProperties(article, articleByIdVo);
        articleByIdVo.setTags(tags);
        return ResponseResult.okResult(articleByIdVo);
    }
}

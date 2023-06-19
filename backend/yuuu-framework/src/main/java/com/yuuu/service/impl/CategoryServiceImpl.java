package com.yuuu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuuu.constants.SystemConstants;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.dto.CategoryDto;
import com.yuuu.domain.entity.Article;
import com.yuuu.domain.entity.Category;
import com.yuuu.domain.vo.CategoryByIdVo;
import com.yuuu.domain.vo.CategoryListVo;
import com.yuuu.domain.vo.CategoryVo;
import com.yuuu.domain.vo.PageVo;
import com.yuuu.enums.AppHttpCodeEnum;
import com.yuuu.mapper.CategoryMapper;
import com.yuuu.service.ArticleService;
import com.yuuu.service.CategoryService;
import com.yuuu.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-03-02 19:37:31
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        //查询发布的文章
        LambdaQueryWrapper<Article> articleQueryWrapper = new LambdaQueryWrapper<>();
        articleQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> list = articleService.list(articleQueryWrapper);
        //获得文章分类id，并且去重
        Set<Long> categoryId = list.stream().map(Article::getCategoryId).collect(Collectors.toSet());
        //查询分类表
        List<Category> categories = listByIds(categoryId);
        categories = categories.stream()
                .filter(category -> SystemConstants.CATEGORY_STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult listAllCategory() {
        List<Category> list = list();
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult getList(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(name)){
            queryWrapper.like(Category::getName,name);
        }
        if (StringUtils.hasText(status)){
            queryWrapper.eq(Category::getStatus,status);
        }
        Page<Category> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        List<Category> records = page.getRecords();
        List<CategoryListVo> categoryListVos = BeanCopyUtils.copyBeanList(records, CategoryListVo.class);
        return ResponseResult.okResult(new PageVo(categoryListVos,page.getTotal()));
    }

    @Override
    public ResponseResult addCategory(CategoryDto categoryDto) {
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        save(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getCategoryById(Long id) {
        Category category = getById(id);
        CategoryByIdVo categoryByIdVo = BeanCopyUtils.copyBean(category, CategoryByIdVo.class);
        return ResponseResult.okResult(categoryByIdVo);
    }

    @Override
    public ResponseResult updateCategory(CategoryByIdVo category) {
        Category category1 = BeanCopyUtils.copyBean(category, Category.class);
        updateById(category1);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteCategory(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult changeStatus(CategoryByIdVo categoryByIdVo) {
        Category category = getById(categoryByIdVo.getId());
        category.setStatus(categoryByIdVo.getStatus());
        boolean b = updateById(category);
        if (b){
            return ResponseResult.okResult();
        }else{
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }


}


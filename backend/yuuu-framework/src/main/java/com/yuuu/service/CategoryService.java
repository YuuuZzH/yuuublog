package com.yuuu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuuu.domain.dto.CategoryDto;
import com.yuuu.domain.entity.Category;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.vo.CategoryByIdVo;

/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-03-02 19:37:21
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    ResponseResult listAllCategory();

    ResponseResult getList(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult addCategory(CategoryDto categoryDto);

    ResponseResult getCategoryById(Long id);

    ResponseResult updateCategory(CategoryByIdVo category);

    ResponseResult deleteCategory(Long id);


    ResponseResult changeStatus(CategoryByIdVo categoryByIdVo);
}


package com.yuuu.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.dto.CategoryDto;
import com.yuuu.domain.dto.UserChangeDto;
import com.yuuu.domain.entity.Category;
import com.yuuu.domain.vo.CategoryByIdVo;
import com.yuuu.domain.vo.ExcelCategoryVo;
import com.yuuu.enums.AppHttpCodeEnum;
import com.yuuu.exception.SystemException;
import com.yuuu.service.CategoryService;
import com.yuuu.utils.BeanCopyUtils;
import com.yuuu.utils.SecurityUtils;
import com.yuuu.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @BelongsProject:YuuuBlog
 * @BelongsPackage:com.yuuu.controller
 * @Author:Yuuu。
 * @CreateTime:2023-03-16 19:45
 * @Description:
 * @Version:1.0
 */
@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("listAllCategory")
    public ResponseResult listAllCategory() {
        return categoryService.listAllCategory();
    }

    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx", response);
            //获取需要导出的数据
            List<Category> list = categoryService.list();
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(list, ExcelCategoryVo.class);
            //把数据写到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class)
                    .autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);
        } catch (Exception e) {
//            e.printStackTrace();
            //异常响应
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }

    @GetMapping("/list")
    public ResponseResult getList(Integer pageNum,Integer pageSize,String name,String status){
        return categoryService.getList(pageNum,pageSize,name,status);
    }

    @PostMapping
    public ResponseResult addCategory(@RequestBody CategoryDto categoryDto){
        if (!SecurityUtils.isAdmin()){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return categoryService.addCategory(categoryDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getCategoryById(@PathVariable("id") Long id){
        return categoryService.getCategoryById(id);
    }

    @PutMapping
    public ResponseResult updateCategory(@RequestBody CategoryByIdVo category){
        if (!SecurityUtils.isAdmin()){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return categoryService.updateCategory(category);
    }

    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody CategoryByIdVo categoryByIdVo){
        if (!SecurityUtils.isAdmin()){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return categoryService.changeStatus(categoryByIdVo);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteCategory(@PathVariable Long id){
        if (!SecurityUtils.isAdmin()){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return categoryService.deleteCategory(id);
    }
}

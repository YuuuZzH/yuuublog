package com.yuuu.controller;

import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.dto.TagDto;
import com.yuuu.domain.dto.TagListDto;
import com.yuuu.domain.vo.PageVo;
import com.yuuu.enums.AppHttpCodeEnum;
import com.yuuu.exception.SystemException;
import com.yuuu.service.TagService;
import com.yuuu.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @BelongsProject:YuuuBlog
 * @BelongsPackage:com.yuuu.controller
 * @Author:Yuuu。
 * @CreateTime:2023-03-08 15:29
 * @Description:
 * @Version:1.0
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageSize, Integer pageNum, TagListDto tagListDto){
        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }
    @PostMapping
    public ResponseResult addTag(@RequestBody TagListDto tagListDto){
        if (!SecurityUtils.isAdmin()){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return tagService.addTag(tagListDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable("id") Long id){
        if (!SecurityUtils.isAdmin()){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return tagService.deleteTag(id);
    }

    @GetMapping("/{id}")
    public ResponseResult getTagById(@PathVariable("id") Long id){
        return tagService.getTagById(id);
    }

    @PutMapping
    public ResponseResult updateTag(@RequestBody TagDto tagDto){
        if (!SecurityUtils.isAdmin()){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        tagDto.setId(Long.valueOf(tagDto.getId()));
        return tagService.updateTag(tagDto);
    }

    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        return tagService.listAllTag();
    }
}

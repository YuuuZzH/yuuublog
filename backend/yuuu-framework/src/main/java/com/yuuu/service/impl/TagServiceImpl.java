package com.yuuu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuuu.service.TagService;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.dto.TagDto;
import com.yuuu.domain.dto.TagListDto;
import com.yuuu.domain.entity.Tag;
import com.yuuu.domain.vo.PageVo;
import com.yuuu.domain.vo.TagVo;
import com.yuuu.enums.AppHttpCodeEnum;
import com.yuuu.mapper.TagMapper;
import com.yuuu.utils.BeanCopyUtils;
import com.yuuu.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-03-08 15:24:55
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        //分页
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName())
                .like(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());
        Page<Tag> page = new Page(pageNum,pageSize);
        page(page,queryWrapper);
        List<Tag> tagList = page.getRecords();
        List<TagVo> tagListVos = BeanCopyUtils.copyBeanList(tagList, TagVo.class);
        //封装
        return ResponseResult.okResult(new PageVo(tagListVos,page.getTotal()));
    }

    @Override
    public ResponseResult addTag(TagListDto tagListDto) {
        Tag tag = new Tag();
        Long userId = SecurityUtils.getUserId();
        tag.setCreateBy(userId);
        tag.setName(tagListDto.getName());
        tag.setRemark(tagListDto.getRemark());
        tag.setCreateTime(new Date());
        tag.setUpdateTime(new Date());
        if (save(tag)){
            return ResponseResult.okResult();
        }else {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public ResponseResult deleteTag(Long id) {
        boolean b = removeById(id);
        if (b){
            return ResponseResult.okResult();
        }else{
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public ResponseResult getTagById(Long id) {
        Tag tag = getById(id);
        if (Objects.nonNull(tag)){
            TagVo tagVo = BeanCopyUtils.copyBean(tag, TagVo.class);
            return ResponseResult.okResult(tagVo);
        }else{
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public ResponseResult updateTag(TagDto tagDto) {
        Long userId = SecurityUtils.getUserId();
        Tag tag = new Tag();
        tag.setId(tagDto.getId());
        tag.setName(tagDto.getName());
        tag.setRemark(tagDto.getRemark());
        tag.setUpdateTime(new Date());
        tag.setUpdateBy(userId);
        boolean b = updateById(tag);
        if (b){
            return ResponseResult.okResult();
        }else{
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public ResponseResult listAllTag() {
        return ResponseResult.okResult(BeanCopyUtils.copyBeanList(list(),TagVo.class));
    }
}


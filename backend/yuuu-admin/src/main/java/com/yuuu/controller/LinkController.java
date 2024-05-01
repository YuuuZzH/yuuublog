package com.yuuu.controller;

import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.dto.AddLinkDto;
import com.yuuu.domain.dto.LinkListDto;
import com.yuuu.domain.dto.UpdateLinkDto;
import com.yuuu.enums.AppHttpCodeEnum;
import com.yuuu.exception.SystemException;
import com.yuuu.service.LinkService;
import com.yuuu.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @BelongsProject:YuuuBlog
 * @BelongsPackage:com.yuuu.controller
 * @Author:Yuuu。
 * @CreateTime:2023-03-16 20:10
 * @Description:
 * @Version:1.0
 */
@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('content:link:list')")
    public ResponseResult linkList(Integer pageNum, Integer pageSize, LinkListDto linkListDto){
        return linkService.linkList(pageNum,pageSize,linkListDto);
    }

    @PostMapping
    @PreAuthorize("@ps.hasPermission('content:link:add')")
    public ResponseResult addLink(@RequestBody AddLinkDto addLinkDto){
        if (!SecurityUtils.isAdmin()){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return linkService.addLink(addLinkDto);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('content:link:query')")
    public ResponseResult getLinkById(@PathVariable("id") Long id){
        return linkService.getLinkById(id);
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('content:link:edit')")
    public ResponseResult updateLink(@RequestBody UpdateLinkDto updateLinkDto){
        if (!SecurityUtils.isAdmin()){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        updateLinkDto.setId(Long.valueOf(updateLinkDto.getId()));
        return linkService.updateLink(updateLinkDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('content:link:remove')")
    public ResponseResult deleteLinkById(@PathVariable("id") Long id){
        if (!SecurityUtils.isAdmin()){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return linkService.deleteLinkById(id);
    }

}

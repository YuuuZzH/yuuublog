package com.yuuu.controller;

import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.dto.AddLinkDto;
import com.yuuu.domain.dto.LinkListDto;
import com.yuuu.domain.dto.UpdateLinkDto;
import com.yuuu.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseResult linkList(Integer pageNum, Integer pageSize, LinkListDto linkListDto){
        return linkService.linkList(pageNum,pageSize,linkListDto);
    }

    @PostMapping
    public ResponseResult addLink(@RequestBody AddLinkDto addLinkDto){
        return linkService.addLink(addLinkDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getLinkById(@PathVariable("id") Long id){
        return linkService.getLinkById(id);
    }

    @PutMapping
    public ResponseResult updateLink(@RequestBody UpdateLinkDto updateLinkDto){
        updateLinkDto.setId(Long.valueOf(updateLinkDto.getId()));
        return linkService.updateLink(updateLinkDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteLinkById(@PathVariable("id") Long id){
        return linkService.deleteLinkById(id);
    }

}

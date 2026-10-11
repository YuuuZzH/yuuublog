package com.yuuu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.dto.AddLinkDto;
import com.yuuu.domain.dto.LinkListDto;
import com.yuuu.domain.dto.UpdateLinkDto;
import com.yuuu.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-03-04 08:56:02
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult linkList(Integer pageNum, Integer pageSize, LinkListDto linkListDto);

    ResponseResult addLink(AddLinkDto addLinkDto);

    ResponseResult getLinkById(Long id);

    ResponseResult updateLink(UpdateLinkDto updateLinkDto);

    ResponseResult deleteLinkById(Long id);
}


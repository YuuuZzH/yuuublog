package com.yuuu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuuu.service.LinkService;
import com.yuuu.constants.SystemConstants;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.dto.AddLinkDto;
import com.yuuu.domain.dto.LinkListDto;
import com.yuuu.domain.dto.UpdateLinkDto;
import com.yuuu.domain.entity.Link;
import com.yuuu.domain.vo.LinkVo;
import com.yuuu.domain.vo.PageVo;
import com.yuuu.enums.AppHttpCodeEnum;
import com.yuuu.mapper.LinkMapper;
import com.yuuu.utils.BeanCopyUtils;
import com.yuuu.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-03-04 08:56:02
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        //查询所有审核通过的友链
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        //审核通过条件
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> list = list(queryWrapper);
        //转换Vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(list, LinkVo.class);
        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult linkList(Integer pageNum, Integer pageSize, LinkListDto linkListDto) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(linkListDto.getName())){
            queryWrapper.like(Link::getName,linkListDto.getName());
        }
        if (StringUtils.hasText(linkListDto.getStatus())) {
            queryWrapper.like(Link::getStatus,linkListDto.getStatus());
        }
        Page<Link> page = new Page<>();
        page(page,queryWrapper);
        List<Link> records = page.getRecords();
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(records, LinkVo.class);
        PageVo pageVo = new PageVo(linkVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addLink(AddLinkDto addLinkDto) {
        Long userId = SecurityUtils.getUserId();
        Link link = new Link();
        link.setCreateBy(userId);
        link.setUpdateBy(userId);
        link.setAddress(addLinkDto.getAddress());
        link.setDescription(addLinkDto.getDescription());
        link.setLogo(addLinkDto.getLogo());
        link.setStatus(addLinkDto.getStatus());
        link.setName(addLinkDto.getName());
        link.setCreateTime(new Date());
        link.setUpdateTime(new Date());
        boolean save = save(link);
        if (save){
            return ResponseResult.okResult();
        }else {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public ResponseResult getLinkById(Long id) {
        Link link = getById(id);
        LinkVo linkVo = BeanCopyUtils.copyBean(link, LinkVo.class);
        return ResponseResult.okResult(linkVo);
    }

    @Override
    public ResponseResult updateLink(UpdateLinkDto updateLinkDto) {
        Long userId = SecurityUtils.getUserId();
        Link link = new Link();
        link.setId(updateLinkDto.getId());
        link.setUpdateBy(userId);
        link.setUpdateTime(new Date());
        link.setName(updateLinkDto.getName());
        link.setAddress(updateLinkDto.getAddress());
        link.setDescription(updateLinkDto.getDescription());
        link.setLogo(updateLinkDto.getLogo());
        link.setStatus(updateLinkDto.getStatus());
        boolean b = updateById(link);
        if (b){
            return ResponseResult.okResult();
        }else {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public ResponseResult deleteLinkById(Long id) {
        boolean b = removeById(id);
        if (b){
            return ResponseResult.okResult();
        }else{
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }
}


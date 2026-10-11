package com.yuuu.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.dto.SensitiveDto;
import com.yuuu.domain.entity.Sensitive;
import com.yuuu.domain.vo.PageVo;
import com.yuuu.mapper.SensitiveMapper;
import com.yuuu.service.SensitiveService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 敏感词信息表(Sensitive)表服务实现类
 *
 * @author makejava
 * @since 2024-04-30 10:23:26
 */
@Service("sensitiveService")
public class SensitiveServiceImpl extends ServiceImpl<SensitiveMapper, Sensitive> implements SensitiveService {

    @Override
    public ResponseResult selectSensitiveList(SensitiveDto sensitive) {
        LambdaQueryWrapper<Sensitive> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(sensitive.getSensitives()), Sensitive::getSensitives, sensitive.getSensitives());
        // 根据时间查找
        queryWrapper.eq(sensitive.getCreatedTime() != null, Sensitive::getCreatedTime, sensitive.getCreatedTime());
        Page<Sensitive> page = new Page<>(sensitive.getPageNum(), sensitive.getPageSize());
        page(page,queryWrapper);
        return ResponseResult.okResult(new PageVo(page.getRecords(),page.getTotal()));
    }
}


package com.yuuu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.entity.About;
import com.yuuu.mapper.AboutMapper;
import com.yuuu.service.AboutService;
import org.springframework.stereotype.Service;

/**
 * 关于我(About)表服务实现类
 */
@Service("aboutService")
public class AboutServiceImpl extends ServiceImpl<AboutMapper, About> implements AboutService {

    @Override
    public ResponseResult getAbout() {
        LambdaQueryWrapper<About> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(About::getId);
        // 只取第一条：站点关于我只有一份配置
        queryWrapper.last("limit 1");
        About about = getOne(queryWrapper);
        return ResponseResult.okResult(about == null ? new About() : about);
    }

    @Override
    public ResponseResult saveAbout(About about) {
        if (about == null) {
            return ResponseResult.errorResult(500, "参数不能为空");
        }
        // id 为空或非持久化的 id 时按新增处理
        boolean result;
        if (about.getId() == null || getById(about.getId()) == null) {
            about.setId(null);
            result = save(about);
        } else {
            result = updateById(about);
        }
        if (!result) {
            return ResponseResult.errorResult(500, "保存失败");
        }
        return ResponseResult.okResult(about);
    }

    @Override
    public ResponseResult getAboutForFront() {
        LambdaQueryWrapper<About> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(About::getStatus, "0");
        queryWrapper.orderByAsc(About::getId);
        queryWrapper.last("limit 1");
        About about = getOne(queryWrapper);
        return ResponseResult.okResult(about == null ? new About() : about);
    }
}

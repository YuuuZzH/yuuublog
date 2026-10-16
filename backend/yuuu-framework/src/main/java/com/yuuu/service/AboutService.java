package com.yuuu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.entity.About;

/**
 * 关于我(About)表服务接口
 */
public interface AboutService extends IService<About> {

    /**
     * 获取关于我配置（取唯一一条记录，不存在时返回空对象）
     */
    ResponseResult getAbout();

    /**
     * 保存关于我配置（存在则更新，不存在则新增）
     */
    ResponseResult saveAbout(About about);

    /**
     * 前台展示用的配置：只返回启用状态
     */
    ResponseResult getAboutForFront();
}

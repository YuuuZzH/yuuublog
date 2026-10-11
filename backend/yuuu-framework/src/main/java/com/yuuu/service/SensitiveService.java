package com.yuuu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.dto.SensitiveDto;
import com.yuuu.domain.entity.Sensitive;

/**
 * 敏感词信息表(Sensitive)表服务接口
 *
 * @author makejava
 * @since 2024-04-30 10:23:26
 */
public interface SensitiveService extends IService<Sensitive> {

    ResponseResult selectSensitiveList(SensitiveDto sensitive);
}


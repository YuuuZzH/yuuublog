package com.yuuu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.dto.QueryLogDto;
import com.yuuu.domain.entity.LoginConfig;

import java.util.List;


/**
 * (LoginConfig)表服务接口
 *
 * @author makejava
 * @since 2024-01-23 16:57:41
 */
public interface LoginConfigService extends IService<LoginConfig> {

    ResponseResult queryLogByLogin(QueryLogDto dto);

    ResponseResult deleteLoginLog(Long[] ids);

    ResponseResult queryLogByVisitor(boolean nearly);
}

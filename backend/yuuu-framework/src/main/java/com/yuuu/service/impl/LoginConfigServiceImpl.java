package com.yuuu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuuu.domain.entity.LoginConfig;
import com.yuuu.mapper.LoginConfigMapper;
import com.yuuu.service.LoginConfigService;
import org.springframework.stereotype.Service;

/**
 * (LoginConfig)表服务实现类
 *
 * @author makejava
 * @since 2024-01-23 16:57:41
 */
@Service
public class LoginConfigServiceImpl extends ServiceImpl<LoginConfigMapper, LoginConfig> implements LoginConfigService {

}

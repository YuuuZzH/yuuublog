package com.yuuu.service;

import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.dto.UserLoginDto;
import com.yuuu.domain.entity.User;

/**
 * @BelongsProject:YuuuBlog
 * @BelongsPackage:com.yuuu.service
 * @Author:Yuuu。
 * @CreateTime:2023-03-04 09:49
 * @Description:
 * @Version:1.0
 */
public interface BlogLoginService {
    ResponseResult login(UserLoginDto user);


    ResponseResult logout();
}

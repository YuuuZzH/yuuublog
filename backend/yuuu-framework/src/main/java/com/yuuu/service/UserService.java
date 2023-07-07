package com.yuuu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.dto.UpdatePasswordDto;
import com.yuuu.domain.dto.UserAddDto;
import com.yuuu.domain.dto.UserChangeDto;
import com.yuuu.domain.dto.UserListDto;
import com.yuuu.domain.entity.User;
import com.yuuu.domain.vo.UserUpdateVo;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-03-05 09:41:29
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult getUserList(Integer pageNum, Integer pageSize, UserListDto userListDto);

    ResponseResult addUser(UserAddDto userAddDto);

    ResponseResult deleteUser(Long id);

    ResponseResult getUserById(Long id);

    ResponseResult changeStatus(UserChangeDto user);

    ResponseResult updateUser(UserUpdateVo userUpdateVo);

    ResponseResult updatePassword(UpdatePasswordDto user);
}


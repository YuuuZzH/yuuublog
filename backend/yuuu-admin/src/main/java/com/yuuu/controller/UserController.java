package com.yuuu.controller;

import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.dto.RoleChangeDto;
import com.yuuu.domain.dto.UserAddDto;
import com.yuuu.domain.dto.UserChangeDto;
import com.yuuu.domain.dto.UserListDto;
import com.yuuu.domain.vo.UserUpdateVo;
import com.yuuu.enums.AppHttpCodeEnum;
import com.yuuu.exception.SystemException;
import com.yuuu.service.UserService;
import com.yuuu.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @BelongsProject:YuuuBlog
 * @BelongsPackage:com.yuuu.controller
 * @Author:Yuuu。
 * @CreateTime:2023-03-18 21:13
 * @Description:
 * @Version:1.0
 */
@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('system:user:list')")
    public ResponseResult getUserList(Integer pageNum, Integer pageSize, UserListDto userListDto){
        return userService.getUserList(pageNum,pageSize,userListDto);
    }

    @PreAuthorize("@ps.hasPermission('system:user:add')")
    @PostMapping
    public ResponseResult addUser(@RequestBody UserAddDto userAddDto){
        if (!SecurityUtils.isAdmin()){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return userService.addUser(userAddDto);
    }

    @PreAuthorize("@ps.hasPermission('system:user:remove')")
    @DeleteMapping("/{id}")
    public ResponseResult deleteUser(@PathVariable("id") Long id){
        if (!SecurityUtils.isAdmin()){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return userService.deleteUser(id);
    }

    @PreAuthorize("@ps.hasPermission('system:user:query')")
    @GetMapping("/{id}")
    public ResponseResult getUserById(@PathVariable("id") Long id){
        return userService.getUserById(id);
    }


    // 修改用户
    @PutMapping()
    @PreAuthorize("@ps.hasPermission('system:user:edit')")
    public ResponseResult updateUser(@RequestBody UserUpdateVo userUpdateVo){
        if (!SecurityUtils.isAdmin()){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        // 更新用户接口
        return userService.updateUser(userUpdateVo);
    }

    @PutMapping("/changeStatus")
    @PreAuthorize("@ps.hasPermission('system:user:edit')")
    public ResponseResult changeStatus(@RequestBody UserChangeDto userChangeDto){
        if (!SecurityUtils.isAdmin()){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return userService.changeStatus(userChangeDto);
    }

}

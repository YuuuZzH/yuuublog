package com.yuuu.controller;

import com.yuuu.domain.ResponseResult;
import com.yuuu.enums.AppHttpCodeEnum;
import com.yuuu.exception.SystemException;
import com.yuuu.service.RoleService;
import com.yuuu.domain.dto.AddRoleDto;
import com.yuuu.domain.dto.RoleChangeDto;
import com.yuuu.domain.dto.RoleListDto;
import com.yuuu.domain.dto.RoleUpdateDto;
import com.yuuu.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @BelongsProject:YuuuBlog
 * @BelongsPackage:com.yuuu.controller
 * @Author:Yuuu。
 * @CreateTime:2023-03-17 16:12
 * @Description:
 * @Version:1.0
 */
@RestController
@RequestMapping("/system/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseResult roleList(Integer pageNum, Integer pageSize, RoleListDto roleListDto){
        return roleService.roleList(pageNum,pageSize,roleListDto);
    }

    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody RoleChangeDto roleChangeDto){
        if (!SecurityUtils.isAdmin()){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return roleService.changeStatus(roleChangeDto);
    }

    @PostMapping
    public ResponseResult addRole(@RequestBody AddRoleDto addRoleDto){
        if (!SecurityUtils.isAdmin()){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return roleService.addRole(addRoleDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getRoleById(@PathVariable("id") Long id){
        return roleService.getRoleById(id);
    }

    @PutMapping
    public ResponseResult updateRole(@RequestBody RoleUpdateDto roleUpdateDto){
        if (!SecurityUtils.isAdmin()){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return roleService.updateRole(roleUpdateDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteById(@PathVariable("id") Long id){
        if (!SecurityUtils.isAdmin()){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return roleService.deleteById(id);
    }

    @GetMapping("/listAllRole")
    public ResponseResult listAllRole(){
        return roleService.listAllRole();
    }



}

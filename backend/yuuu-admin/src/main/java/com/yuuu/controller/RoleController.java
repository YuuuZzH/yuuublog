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
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("@ps.hasPermission('system:role:list')")
    public ResponseResult roleList(Integer pageNum, Integer pageSize, RoleListDto roleListDto){
        return roleService.roleList(pageNum,pageSize,roleListDto);
    }

    @PutMapping("/changeStatus")
    @PreAuthorize("@ps.hasPermission('system:role:edit')")
    public ResponseResult changeStatus(@RequestBody RoleChangeDto roleChangeDto){
        if (!SecurityUtils.isAdmin()){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return roleService.changeStatus(roleChangeDto);
    }

    @PostMapping
    @PreAuthorize("@ps.hasPermission('system:role:add')")
    public ResponseResult addRole(@RequestBody AddRoleDto addRoleDto){
        if (!SecurityUtils.isAdmin()){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return roleService.addRole(addRoleDto);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('system:role:query')")
    public ResponseResult getRoleById(@PathVariable("id") Long id){
        return roleService.getRoleById(id);
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('system:role:edit')")
    public ResponseResult updateRole(@RequestBody RoleUpdateDto roleUpdateDto){
        if (!SecurityUtils.isAdmin()){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return roleService.updateRole(roleUpdateDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('system:role:remove')")
    public ResponseResult deleteById(@PathVariable("id") Long id){
        if (!SecurityUtils.isAdmin()){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return roleService.deleteById(id);
    }

    @GetMapping("/listAllRole")
    @PreAuthorize("@ps.hasPermission('system:role:list')")
    public ResponseResult listAllRole(){
        return roleService.listAllRole();
    }



}

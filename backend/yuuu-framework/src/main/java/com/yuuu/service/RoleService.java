package com.yuuu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.dto.AddRoleDto;
import com.yuuu.domain.dto.RoleChangeDto;
import com.yuuu.domain.dto.RoleListDto;
import com.yuuu.domain.dto.RoleUpdateDto;
import com.yuuu.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-03-08 19:17:58
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult roleList(Integer pageNum, Integer pageSize, RoleListDto roleListDto);

    ResponseResult changeStatus(RoleChangeDto roleChangeDto);

    ResponseResult addRole(AddRoleDto addRoleDto);

    ResponseResult getRoleById(Long id);

    ResponseResult updateRole(RoleUpdateDto roleUpdateDto);

    ResponseResult deleteById(Long id);

    ResponseResult listAllRole();
}


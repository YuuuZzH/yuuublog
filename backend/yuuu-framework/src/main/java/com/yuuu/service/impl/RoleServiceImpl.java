package com.yuuu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuuu.domain.entity.UserRole;
import com.yuuu.exception.SystemException;
import com.yuuu.service.RoleService;
import com.yuuu.constants.SystemConstants;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.dto.AddRoleDto;
import com.yuuu.domain.dto.RoleChangeDto;
import com.yuuu.domain.dto.RoleListDto;
import com.yuuu.domain.dto.RoleUpdateDto;
import com.yuuu.domain.entity.Role;
import com.yuuu.domain.entity.RoleMenu;
import com.yuuu.domain.vo.PageVo;
import com.yuuu.domain.vo.RoleAllVo;
import com.yuuu.domain.vo.RoleByIdVo;
import com.yuuu.domain.vo.RoleListVo;
import com.yuuu.enums.AppHttpCodeEnum;
import com.yuuu.mapper.RoleMapper;
import com.yuuu.service.RoleMenuService;
import com.yuuu.service.UserRoleService;
import com.yuuu.utils.BeanCopyUtils;
import com.yuuu.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-03-08 19:17:58
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //如果是管理员，只需要返回admin
        if (SecurityUtils.isAdmin()){
            List<String> roles = new ArrayList<>();
            roles.add("admin");
            return roles;
        }
        //否则返回用户具有的所有角色
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult roleList(Integer pageNum, Integer pageSize, RoleListDto roleListDto) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(roleListDto.getRoleName())){
            queryWrapper.like(Role::getRoleName,roleListDto.getRoleName());
        }
        if (StringUtils.hasText(roleListDto.getStatus())){
            queryWrapper.eq(Role::getStatus,roleListDto.getStatus());
        }
        queryWrapper.ne(Role::getId,1);
        Page<Role> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        List<Role> roles = page.getRecords();
        List<RoleListVo> roleListVos = BeanCopyUtils.copyBeanList(roles, RoleListVo.class);
        return ResponseResult.okResult(new PageVo(roleListVos,page.getTotal()));
    }

    @Override
    public ResponseResult changeStatus(RoleChangeDto roleChangeDto) {
        Role role = getById(roleChangeDto.getRoleId());
        role.setStatus(roleChangeDto.getStatus());
        boolean b = updateById(role);
        if (b){
            return ResponseResult.okResult();
        }else{
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseResult addRole(AddRoleDto addRoleDto) {
        Role role = BeanCopyUtils.copyBean(addRoleDto, Role.class);
        Long userId = SecurityUtils.getUserId();
        role.setCreateBy(userId);
        role.setCreateTime(new Date());
        role.setUpdateBy(userId);
        role.setUpdateTime(new Date());
        save(role);
        List<RoleMenu> roleMenus = addRoleDto.getMenuIds().stream()
                .map(menuId -> new RoleMenu(role.getId(), menuId))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenus);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRoleById(Long id) {
        Role role = getById(id);
        RoleByIdVo roleByIdVo = BeanCopyUtils.copyBean(role, RoleByIdVo.class);
        return ResponseResult.okResult(roleByIdVo);
    }

    @Override
    @Transactional
    public ResponseResult updateRole(RoleUpdateDto roleUpdateDto) {
        Role role = BeanCopyUtils.copyBean(roleUpdateDto, Role.class);
        Long userId = SecurityUtils.getUserId();
        role.setUpdateBy(userId);
        role.setUpdateTime(new Date());
        updateById(role);

        List<Long> menuIds = roleUpdateDto.getMenuIds();
        if (menuIds != null && menuIds.size() > 0) {
            baseMapper.deleteRoleMenuByRoleId(role.getId());
            baseMapper.insertRoleMenu(menuIds, role.getId());
        }else {
            baseMapper.deleteRoleMenuByRoleId(role.getId());
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteById(Long id) {
        // 角色是否被引用
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getRoleId,id);
        List<UserRole> list = userRoleService.list(queryWrapper);
        if (!CollectionUtils.isEmpty(list)){
            throw new SystemException(AppHttpCodeEnum.ROLE_USED);
        }
        // 清除中间表
        LambdaQueryWrapper<RoleMenu> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(RoleMenu::getRoleId,id);
        roleMenuService.remove(queryWrapper1);
        boolean b = removeById(id);
        if (b){
            return ResponseResult.okResult();
        }else{
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public ResponseResult listAllRole() {
        //查询角色，供用户选择
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getStatus, SystemConstants.STATUS_NORMAL);
        List<Role> list = list(queryWrapper);
        List<RoleAllVo> roleAllVos = BeanCopyUtils.copyBeanList(list, RoleAllVo.class);
        return ResponseResult.okResult(roleAllVos);
    }
}


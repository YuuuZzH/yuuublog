package com.yuuu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuuu.service.RoleMenuService;
import com.yuuu.domain.entity.RoleMenu;
import com.yuuu.mapper.RoleMenuMapper;
import org.springframework.stereotype.Service;

/**
 * 角色和菜单关联表(RoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2023-03-17 22:00:52
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

}


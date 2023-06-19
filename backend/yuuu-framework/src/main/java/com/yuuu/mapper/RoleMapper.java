package com.yuuu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuuu.domain.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-08 19:17:56
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(@Param("userId") Long userId);

    void deleteRoleMenuByRoleId(Long id);

    void insertRoleMenu(@Param("menuIds") List<Long> menuIds,@Param("roleId") Long id);
}

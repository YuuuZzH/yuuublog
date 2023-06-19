package com.yuuu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuuu.domain.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-04 09:34:27
 */
public interface UserMapper extends BaseMapper<User> {
    void deleteUserRoleByUserId(Long id);

    void insertUserRole(@Param("roleIds") List<Long> roleIds, @Param("userId") Long userId);

    
}

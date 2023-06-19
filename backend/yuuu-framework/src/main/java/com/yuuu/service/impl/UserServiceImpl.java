package com.yuuu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuuu.constants.SystemConstants;
import com.yuuu.domain.dto.UserChangeDto;
import com.yuuu.domain.entity.Role;
import com.yuuu.domain.entity.UserRole;
import com.yuuu.domain.vo.*;
import com.yuuu.service.RoleService;
import com.yuuu.service.UserRoleService;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.dto.UserAddDto;
import com.yuuu.domain.dto.UserListDto;
import com.yuuu.domain.entity.User;
import com.yuuu.enums.AppHttpCodeEnum;
import com.yuuu.exception.SystemException;
import com.yuuu.mapper.UserMapper;
import com.yuuu.service.UserService;
import com.yuuu.utils.BeanCopyUtils;
import com.yuuu.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-03-05 09:41:30
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserMapper userMapper;


    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //查询信息
        User user = getById(userId);
        //封装Vo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        //校验请求是否为当前用户发出
        Long userId = SecurityUtils.getUserId();
        if (userId == user.getId()){
            throw new SystemException(AppHttpCodeEnum.UPDATE_ERROR);
        }
        updateById(user);
        return ResponseResult.okResult();
    }

    //TODO 注册功能完善
    @Override
    public ResponseResult register(User user) {
        //非空校验
        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.NOT_NULL);
        }
        if (!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NOT_NULL);
        }
        if (!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.NOT_NULL);
        }
        if (!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.NOT_NULL);
        }

        //唯一性校验
        if (userNameIsExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (emailIsExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        if (nickNameIsExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NOT_UNIQUE);
        }
        //密码加密
        String password = passwordEncoder.encode(user.getPassword());

        user.setPassword(password);
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUserList(Integer pageNum, Integer pageSize, UserListDto userListDto) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(userListDto.getUserName())){
            queryWrapper.like(User::getUserName,userListDto.getUserName());
        }
        if (StringUtils.hasText(userListDto.getPhoneNumber())){
            queryWrapper.eq(User::getPhoneNumber,userListDto.getPhoneNumber());
        }
        if (StringUtils.hasText(userListDto.getStatus())){
            queryWrapper.eq(User::getStatus,userListDto.getStatus());
        }
        Page<User> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        List<UserListVo> userListVos = BeanCopyUtils.copyBeanList(page.getRecords(), UserListVo.class);
        return ResponseResult.okResult(new PageVo(userListVos,page.getTotal()));

    }

    @Override
    @Transactional
    public ResponseResult addUser(UserAddDto userAddDto) {
        //非空校验
        if (!StringUtils.hasText(userAddDto.getUserName())){
            throw new SystemException(AppHttpCodeEnum.NOT_NULL);
        }
        if (!StringUtils.hasText(userAddDto.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NOT_NULL);
        }
        if (!StringUtils.hasText(userAddDto.getPassword())){
            throw new SystemException(AppHttpCodeEnum.NOT_NULL);
        }
        if (!StringUtils.hasText(userAddDto.getEmail())){
            throw new SystemException(AppHttpCodeEnum.NOT_NULL);
        }

        //唯一性校验
        if (userNameIsExist(userAddDto.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (emailIsExist(userAddDto.getNickName())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        if (nickNameIsExist(userAddDto.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NOT_UNIQUE);
        }
        //密码加密
        String password = passwordEncoder.encode(userAddDto.getPassword());

        userAddDto.setPassword(password);
        User user = BeanCopyUtils.copyBean(userAddDto, User.class);
        save(user);

        List<UserRole> userRoles = userAddDto.getRoleIds().stream()
                .map(roleId -> new UserRole(user.getId(), roleId))
                .collect(Collectors.toList());
        userRoleService.saveBatch(userRoles);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteUser(Long id) {
        Long userId = SecurityUtils.getUserId();
        if (userId.equals(id)){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        boolean b = removeById(id);
        if (b){
            return ResponseResult.okResult();
        }else{
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public ResponseResult getUserById(Long id) {
        //用户信息
        User user = getById(id);
        UserUpdateVo userUpdateVo = BeanCopyUtils.copyBean(user, UserUpdateVo.class);
        //所有角色的列表
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getStatus, SystemConstants.STATUS_NORMAL);
        List<Role> list = roleService.list(queryWrapper);
        List<RoleAllVo> roleAllVos = BeanCopyUtils.copyBeanList(list, RoleAllVo.class);
        //用户所关联的角色id列表
        LambdaQueryWrapper<UserRole> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(UserRole::getUserId,id);
        List<Long> roleIds = userRoleService.list(queryWrapper1).stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());
        UserByIdVo userByIdVo = new UserByIdVo(roleIds, roleAllVos, userUpdateVo);
        return ResponseResult.okResult(userByIdVo);
    }
    @Override
    public ResponseResult changeStatus(UserChangeDto userChangeDto) {

        User user = getById(userChangeDto.getUserId());
        user.setStatus(userChangeDto.getStatus());
        boolean b = updateById(user);
        if (b){
            return ResponseResult.okResult();
        }else {
            return ResponseResult.errorResult(500, "修改失败");
        }
    }

    @Override
    public ResponseResult updateUser(UserUpdateVo userUpdateVo) {
        User user = BeanCopyUtils.copyBean(userUpdateVo, User.class);
        updateById(user);
        List<Long> roleIds = userUpdateVo.getRoleIds();
        if (roleIds != null && roleIds.size() > 0){
            baseMapper.deleteUserRoleByUserId(user.getId());
            baseMapper.insertUserRole(roleIds,user.getId());
        }else {
            baseMapper.deleteUserRoleByUserId(user.getId());
        }

        return ResponseResult.okResult();
    }

    private boolean userNameIsExist(String s){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,s);
        return count(queryWrapper) > 0;
    }
    private boolean emailIsExist(String s){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail,s);
        return count(queryWrapper) > 0;
    }
    private boolean nickNameIsExist(String s){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName,s);
        return count(queryWrapper) > 0;
    }

}


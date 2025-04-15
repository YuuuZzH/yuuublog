package com.yuuu.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuuu.constants.SystemConstants;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.dto.ChangePassword;
import com.yuuu.domain.dto.UserAddDto;
import com.yuuu.domain.dto.UserChangeDto;
import com.yuuu.domain.dto.UserListDto;
import com.yuuu.domain.entity.*;
import com.yuuu.domain.vo.*;
import com.yuuu.enums.AppHttpCodeEnum;
import com.yuuu.exception.SystemException;
import com.yuuu.mapper.UserMapper;
import com.yuuu.service.RoleService;
import com.yuuu.service.UserRoleService;
import com.yuuu.service.UserService;
import com.yuuu.utils.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.yuuu.constants.ApiUrlConstants.*;
import static com.yuuu.utils.VisitorUtil.emailDesensitization;


/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-03-05 09:41:30
 */
@Service("userService")
@ConfigurationProperties(prefix = "github")
@Data
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private AvatarService avatarService;

    private String clientId;
    private String clientSecret;

    private final RestTemplate restTemplate;

    public UserServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseResult userInfo() {
        // 获取当前用户id
        Long userId = SecurityUtils.getUserId();
        // 查询信息
        User user = getById(userId);
        // 封装Vo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        // 校验请求是否为当前用户发出
        Long userId = SecurityUtils.getUserId();
        if (!Objects.equals(userId, user.getId())) {
            throw new SystemException(AppHttpCodeEnum.UPDATE_ERROR);
        }
        updateById(user);
        return ResponseResult.okResult();
    }


    @Override
    public ResponseResult register(User user) {
        String emailCaptcha = user.getEmailCaptcha();
        // 非空校验
        if (StrUtil.isBlank(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.NOT_NULL);
        }
        if (StrUtil.isBlank(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NOT_NULL);
        }
        if (StrUtil.isBlank(user.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_ISNULL);
        }
        if (StrUtil.isBlank(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_ISNULL);
        }
        if (StrUtil.isBlank(emailCaptcha)) {
            throw new SystemException(AppHttpCodeEnum.CAPTCHA_ISNULL);
        }

        // 唯一性校验
        if (userNameIsExist(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (emailIsExist(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        if (nickNameIsExist(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NOT_UNIQUE);
        }

        String cacheObject = redisCache.getCacheObject(SystemConstants.MAIL_KEY_0 + emailCaptcha);
        if (!emailCaptcha.equals(cacheObject)) {
            return ResponseResult.errorResult(601, "验证码不对诶~");
        } else {
            redisCache.deleteObject(SystemConstants.MAIL_KEY_0 + cacheObject);
        }

        // 密码加密
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setAvatar(avatarService.getRandomAvatar());
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUserList(Integer pageNum, Integer pageSize, UserListDto userListDto) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (!StrUtil.isBlank(userListDto.getUserName())) {
            queryWrapper.like(User::getUserName, userListDto.getUserName());
        }
        if (!StrUtil.isBlank(userListDto.getPhoneNumber())) {
            queryWrapper.like(User::getPhoneNumber, userListDto.getPhoneNumber());
        }
        if (!StrUtil.isBlank(userListDto.getStatus())) {
            queryWrapper.eq(User::getStatus, userListDto.getStatus());
        }
        Long userId = SecurityUtils.getUserId();
        queryWrapper.ne(User::getId,1).ne(User::getId,userId);
        Page<User> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<UserListVo> userListVos = BeanCopyUtils.copyBeanList(page.getRecords(), UserListVo.class);
        userListVos.stream()
                .filter(item -> StrUtil.isNotBlank(item.getPhoneNumber()))
                .forEach(item -> item.setPhoneNumber(PhoneNumberEncryptionUtil.encryptPhoneNumber(item.getPhoneNumber())));
        return ResponseResult.okResult(new PageVo(userListVos, page.getTotal()));

    }

    @Override
    @Transactional
    public ResponseResult addUser(UserAddDto userAddDto) {
        // 非空校验
        if (StrUtil.isBlank(userAddDto.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.NOT_NULL);
        }
        if (StrUtil.isBlank(userAddDto.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NOT_NULL);
        }
        if (StrUtil.isBlank(userAddDto.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_ISNULL);
        }

        if (StrUtil.isBlank(userAddDto.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_ISNULL);
        }

        // 唯一性校验
        if (userNameIsExist(userAddDto.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (emailIsExist(userAddDto.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        if (nickNameIsExist(userAddDto.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NOT_UNIQUE);
        }
        // 密码加密
        String password = passwordEncoder.encode(userAddDto.getPassword());
        if (StrUtil.isNotBlank(userAddDto.getPhoneNumber())){
            String encryptPhoneNumber = PhoneNumberEncryptionUtil.encryptPhoneNumber(userAddDto.getPhoneNumber());
            userAddDto.setPhoneNumber(encryptPhoneNumber);
        }
        userAddDto.setPassword(password);
        User user = BeanCopyUtils.copyBean(userAddDto, User.class);
        user.setAvatar(avatarService.getRandomAvatar());
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
        if (userId.equals(id)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        // 清除中间表
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,id);
        userRoleService.remove(queryWrapper);
        boolean b = removeById(id);
        if (b) {
            return ResponseResult.okResult();
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public ResponseResult getUserById(Long id) {
        // 用户信息
        User user = getById(id);
        UserUpdateVo userUpdateVo = BeanCopyUtils.copyBean(user, UserUpdateVo.class);
        userUpdateVo.setEmail(emailDesensitization(userUpdateVo.getEmail()));
        // 所有角色的列表
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getStatus, SystemConstants.STATUS_NORMAL);
        List<Role> list = roleService.list(queryWrapper);
        List<RoleAllVo> roleAllVos = BeanCopyUtils.copyBeanList(list, RoleAllVo.class);
        // 用户所关联的角色id列表
        LambdaQueryWrapper<UserRole> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(UserRole::getUserId, id);
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
        if (b) {
            return ResponseResult.okResult();
        } else {
            return ResponseResult.errorResult(500, "修改失败");
        }
    }

    @Override
    public ResponseResult updateUser(UserUpdateVo userUpdateVo) {
        User user = BeanCopyUtils.copyBean(userUpdateVo, User.class);
        Long userId = SecurityUtils.getUserId();
        user.setUpdateBy(userId);
        user.setUpdateTime(new Date());
        updateById(user);
        List<Long> roleIds = userUpdateVo.getRoleIds();
        if (roleIds != null && roleIds.size() > 0) {
            baseMapper.deleteUserRoleByUserId(user.getId());
            baseMapper.insertUserRole(roleIds, user.getId());
        } else {
            baseMapper.deleteUserRoleByUserId(user.getId());
        }

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updatePassword(ChangePassword user) {
        String email = user.getEmailPassword();
        String password = user.getConfirmNewPassword();
        String captcha = user.getEmailCaptchaUpdate();
        if (StrUtil.isBlank(email)) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_ISNULL);
        }
        if (StrUtil.isBlank(password)) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_ISNULL);
        }
        if (StrUtil.isBlank(captcha)) {
            throw new SystemException(AppHttpCodeEnum.CAPTCHA_ISNULL);
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        List<User> list = list(queryWrapper);
        if (list.size() != 1) {
            throw new SystemException(AppHttpCodeEnum.NO_USER);
        }
        String cacheCaptcha = redisCache.getCacheObject(SystemConstants.MAIL_KEY_1 + captcha);
        if (!captcha.equals(cacheCaptcha)) {
            return ResponseResult.errorResult(601, "请再检查一下验证码~");
        } else {
            redisCache.deleteObject(SystemConstants.MAIL_KEY_1 + captcha);
        }
        // 密码加密
        password = passwordEncoder.encode(password);
        User oldUser = list.get(0);
        oldUser.setPassword(password);
        // 修改密码
        boolean b = updateById(oldUser);
        System.out.println("b = " + b);
        if (b) {
            return ResponseResult.okResult();
        } else {
            return ResponseResult.errorResult(639, "修改失败");
        }

    }

    @Override
    public ResponseResult oauth(String code) {

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        BlogUserLoginVo blogUserLoginVo;
        try {
            String oauthToken = getOauthToken(code);
            GithubUser githubUser = getUserInfoWithGithub(oauthToken);

            queryWrapper.eq(User::getGithubId, githubUser.getId());
            User one = getOne(queryWrapper);
            if (one != null) {
                blogUserLoginVo = getBlogUserLoginVo(one);
                return ResponseResult.okResult(blogUserLoginVo);
            } else {
                redisCache.setCacheObject(code,githubUser);
                Map<Integer,String> map = new HashMap<>();
                map.put(999, "未绑定");
                return ResponseResult.okResult(map);
            }
        } catch (IOException e) {
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public ResponseResult oauthGithub(ChangePassword user,String code) {
        String email = user.getEmailPassword();
        String captcha = user.getEmailCaptchaUpdate();
        if (StrUtil.isBlank(email)) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_ISNULL);
        }
        if (StrUtil.isBlank(captcha)) {
            throw new SystemException(AppHttpCodeEnum.CAPTCHA_ISNULL);
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        String cacheCaptcha = redisCache.getCacheObject(SystemConstants.MAIL_KEY_1 + captcha);
        if (!captcha.equals(cacheCaptcha)) {
            return ResponseResult.errorResult(601, "请再检查一下验证码~");
        } else {
            redisCache.deleteObject(SystemConstants.MAIL_KEY_1 + captcha);
        }
        User existingUser = getOne(queryWrapper);
        BlogUserLoginVo blogUserLoginVo;
        GithubUser githubUser = redisCache.getCacheObject(code);
        if (existingUser == null) {
            // 新增
            User userByGithub = new User();
            userByGithub.setGithubId(githubUser.getId());
            userByGithub.setAvatar(githubUser.getAvatarUrl());
            userByGithub.setEmail(user.getEmailPassword());
            userByGithub.setPassword(passwordEncoder.encode(SystemConstants.DEFAULT_PASSWORD));
            userByGithub.setNickName(githubUser.getNodeId());
            userByGithub.setUserName(githubUser.getLogin());
            save(userByGithub);
            // 用户登录
            blogUserLoginVo = getBlogUserLoginVo(userByGithub);
        } else {
            // 修改
            existingUser.setGithubId(githubUser.getId());
            updateById(existingUser);
            // 用户登录
            blogUserLoginVo = getBlogUserLoginVo(existingUser);
        }
        redisCache.deleteObject(code);
        return ResponseResult.okResult(blogUserLoginVo);
    }

    private BlogUserLoginVo getBlogUserLoginVo(User user) {
        // 获取userId，生成token
        String jwt = JwtUtil.createJWT(user.getId().toString());
        // 把用户信息存入redis
        LoginUser loginUser = new LoginUser();
        loginUser.setUser(user);
        loginUser.setPermissions(null);
        redisCache.setCacheObject(SystemConstants.BLOG_LOGIN_KEY + user.getId(), loginUser, 1800, TimeUnit.SECONDS);
        // 把token和userInfo封装返回
        // 把user转换为userInfo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return new BlogUserLoginVo(jwt, userInfoVo);
    }


    private GithubUser getUserInfoWithGithub(String oauthToken) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + oauthToken);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(GITHUB_USER.getUrl(),
                GITHUB_USER.getMethod(), requestEntity, String.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String body = responseEntity.getBody();
            cn.hutool.json.JSONObject jsonObject = JSONUtil.parseObj(body);
            return jsonObject.toBean(GithubUser.class);
        } else {
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }


    private String getOauthToken(String code) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        String requestBody = String.format("{\"client_id\":\"%s\",\"client_secret\":\"%s\",\"code\":\"%s\"}",
                clientId, clientSecret, code);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(GITHUB_ACCESS_TOKEN.getUrl(),
                GITHUB_ACCESS_TOKEN.getMethod(), request, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            // 解析JSON响应，获取access_token字段
            String jsonString = JSON.toJSONString(response);
            JSONObject jsonObject = JSON.parseObject(jsonString);
            JSONObject body = jsonObject.getJSONObject("body");
            Object token = body.get("access_token");
            return token.toString();
        } else {
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }


    private boolean userNameIsExist(String s) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, s);
        return count(queryWrapper) > 0;
    }

    private boolean emailIsExist(String s) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, s);
        return count(queryWrapper) > 0;
    }

    private boolean nickNameIsExist(String s) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName, s);
        return count(queryWrapper) > 0;
    }

}


package com.yuuu.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.dto.QueryLogDto;
import com.yuuu.domain.entity.LoginConfig;
import com.yuuu.domain.entity.LoginUser;
import com.yuuu.domain.vo.PageVo;
import com.yuuu.domain.vo.QueryLogVo;
import com.yuuu.mapper.LoginConfigMapper;
import com.yuuu.service.LoginConfigService;
import com.yuuu.utils.BeanCopyUtils;
import com.yuuu.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.yuuu.utils.VisitorUtil.emailDesensitization;

/**
 * (LoginConfig)表服务实现类
 *
 * @author makejava
 * @since 2024-01-23 16:57:41
 */
@Service
@Slf4j
public class LoginConfigServiceImpl extends ServiceImpl<LoginConfigMapper, LoginConfig> implements LoginConfigService {

    @Override
    public ResponseResult queryLogByLogin(QueryLogDto dto) {
        LambdaQueryWrapper<LoginConfig> queryWrapper = new LambdaQueryWrapper<>();
        // 查询登录用户信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        queryWrapper.eq(LoginConfig::getType, LoginConfig.LOGIN_USER)
                .ne(LoginConfig::getUserName, loginUser.getUser().getUserName());
        queryWrapper.like(StrUtil.isNotBlank(dto.getAddress()), LoginConfig::getAddress, dto.getAddress());
        queryWrapper.like(StrUtil.isNotBlank(dto.getNickName()), LoginConfig::getNickName, dto.getNickName());
        Calendar calendar = Calendar.getInstance();
        if (dto.getNearly() == 0) {
            calendar.add(Calendar.DATE, -7);
        } else {
            calendar.add(Calendar.DATE, -30);
        }
        Date time = calendar.getTime();
        queryWrapper.le(LoginConfig::getLoginTime, new Date());
        queryWrapper.ge(LoginConfig::getLoginTime, time);
        Page<LoginConfig> page = new Page(dto.getPageNo(), dto.getPageSize());
        page(page, queryWrapper);
        List<LoginConfig> records = page.getRecords();
        List<QueryLogVo> vos = BeanCopyUtils.copyBeanList(records, QueryLogVo.class);
        vos.stream().forEach(vo -> vo.setEmail(emailDesensitization(vo.getEmail())));
        PageVo pageVo = new PageVo(vos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult deleteLoginLog(Long[] ids) {
        return ResponseResult.okResult(removeByIds(Arrays.asList(ids)));
    }

    @Override
    public ResponseResult queryLogByVisitor(boolean nearly) {
        LambdaQueryWrapper<LoginConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LoginConfig::getType, LoginConfig.VISITOR);
        Calendar calendar = Calendar.getInstance();
        if (!nearly) {
            calendar.add(Calendar.DATE, -7);
        } else {
            calendar.add(Calendar.DATE, -30);
        }
        Date time = calendar.getTime();
        queryWrapper.le(LoginConfig::getLoginTime, new Date());
        queryWrapper.ge(LoginConfig::getLoginTime, time);
        List<LoginConfig> list = list(queryWrapper);
        return ResponseResult.okResult(list);
    }
}

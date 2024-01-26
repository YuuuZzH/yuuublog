package com.yuuu.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.entity.LoginConfig;
import com.yuuu.domain.entity.User;
import com.yuuu.service.LoginConfigService;
import com.yuuu.service.UserService;
import com.yuuu.utils.IPUtil;
import com.yuuu.utils.IpToAddressUtil;
import com.yuuu.utils.SecurityUtils;
import com.yuuu.utils.UserAgentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author: yuuu
 * Description:
 * Email: ***REMOVED***
 * CreatedTime: 2023-08-22 15:55:15:55
 */
@RestController
public class IPController {

    @Autowired
    private LoginConfigService loginConfigService;

    @PostMapping("/ip")
    public ResponseResult getIp(){
        Long userId = SecurityUtils.getUserId();
        LambdaQueryWrapper<LoginConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LoginConfig::getUserId,userId);
        LoginConfig one = loginConfigService.getOne(queryWrapper);
        HashMap<String,String> map = new HashMap<>();
        map.put("devName",one.getDevName());
        map.put("ip",one.getIp());
        map.put("address",one.getAddress());
        map.put("browser",one.getBrowser());
        map.put("os",one.getOs());
        return new ResponseResult().ok(one);
    }

}

package com.yuuu.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.yuuu.domain.entity.LoginConfig;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author: yuuu
 * Description:
 */
@Data
public class QueryLogVo {
    //用户昵称
    private String nickName;
    //登录ip
    private String ip;
    //登录设备
    private String devName;
    //登录时间
    private Date loginTime;
    //用户邮箱
    private String email;
    //操作系统
    private String os;
    //浏览器
    private String browser;

    private String address;
}

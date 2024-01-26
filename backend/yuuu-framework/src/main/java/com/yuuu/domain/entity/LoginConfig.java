package com.yuuu.domain.entity;

import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (LoginConfig)表实体类
 *
 * @author makejava
 * @since 2024-01-23 16:57:41
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("login_config")
public class LoginConfig  {
    public static final String LOGIN_USER = "1";
    public static final String VISITOR = "2";

    @TableId
    private Long id;

    //用户名
    @TableField("user_name")
    private String userName;
    //用户昵称
    @TableField("nick_name")
    private String nickName;
    //登录ip
    @TableField("ip")
    private String ip;
    //登录设备
    @TableField("dev_name")
    private String devName;
    //登录时间
    @TableField("login_time")
    private Date loginTime;
    //用户邮箱
    @TableField("email")
    private String email;
    //操作系统
    @TableField("os")
    private String os;
    //浏览器
    @TableField("browser")
    private String browser;

    @TableField("address")
    private String address;

    @TableField("user_id")
    private Long userId;

    @TableField("type")
    private String type;



}

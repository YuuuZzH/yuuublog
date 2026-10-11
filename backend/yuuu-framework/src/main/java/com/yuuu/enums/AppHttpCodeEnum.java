package com.yuuu.enums;

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200, "操作成功"),
    // 登录
    NEED_LOGIN(401, "需要登录后操作"),
    NO_OPERATOR_AUTH(403, "无权限操作"),
    SYSTEM_ERROR(500, "出现错误"),
    USERNAME_EXIST(501, "用户名已存在"),
    PHONENUMBER_EXIST(502, "手机号已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    CONTENT_IS_NULL(506, "评论不能为空"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    FILE_TYPE_ERROR(507, "文件格式错误"),
    UPDATE_ERROR(509, "非法修改"),
    NOT_NULL(510, "信息不能为空"),
    NOT_UNIQUE(511, "信息已存在"),
    LOGIN_ERROR(505, "用户名或密码错误"),


    /** *************前台 Blog 异常枚举值 **************/

    EMAIL_ERROR(603, "请检查邮箱或稍后再试"),
    EMAIL_ISNULL(604, "邮箱不能为空"),
    PASSWORD_ISNULL(605, "密码不能为空"),
    CAPTCHA_ISNULL(606, "验证码不能为空"),
    AVATAR_IS_ERROR(608,"图片路径错误"),
    NO_ARTICLE(613,"该文章不存在，请重新输入"),
    INPUT_ERROR(614,"您输入的内容有误"),
    NO_USER(607, "用户尚未注册，请先完成注册"),
    OVER_LOCKING(609, "啊哦，一不小心超限制了，请明天再来吧！"),
    RSA_FAIL(610, "RSA解密失败"),
    TEMPLATE_NOT_FOUND(611, "短信模板不存在"),


    /** *************后台 admin 异常枚举值 **************/
    ROLE_USED(701, "该角色正在使用"),
    NO_PERMISSION(703, "非系统管理员，不允许执行该操作！"),
    USER_USED(702, "该用户为当前登录人，不可删除");

    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage) {
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
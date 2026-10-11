package com.yuuu.exception;

import com.yuuu.enums.AppHttpCodeEnum;

/**
 * @BelongsProject:YuuuBlog
 * @BelongsPackage:com.yuuu.exception
 * @Author:Yuuu。
 * @CreateTime:2023-03-04 16:10
 * @Description:
 * @Version:1.0
 */

public class SystemException extends RuntimeException {

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

}

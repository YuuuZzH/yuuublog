package com.yuuu.handler.exception;

import com.yuuu.domain.ResponseResult;
import com.yuuu.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @BelongsProject:YuuuBlog
 * @BelongsPackage:com.yuuu.handler.exception
 * @Author:Yuuu。
 * @CreateTime:2023-03-04 16:14
 * @Description:
 * @Version:1.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(SystemException.class)
    public ResponseResult systemException(SystemException e){
        //打印异常信息
        log.error("出现了异常！",e );
        //从异常信息中获取内容封装出去
        return ResponseResult.errorResult(e.getCode(),e.getMsg());
    }


}

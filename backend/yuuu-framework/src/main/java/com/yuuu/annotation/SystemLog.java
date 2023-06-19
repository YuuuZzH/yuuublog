package com.yuuu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @BelongsProject:YuuuBlog
 * @BelongsPackage:com.yuuu.annotation
 * @Author:Yuuu。
 * @CreateTime:2023-03-07 15:38
 * @Description:
 * @Version:1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SystemLog {
    String businessName();
}

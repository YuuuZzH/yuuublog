package com.yuuu.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @BelongsProject:YuuuBlog
 * @BelongsPackage:com.yuuu.utils
 * @Author:Yuuu。
 * @CreateTime:2023-03-02 17:01
 * @Description:
 * @Version:1.0
 */
public class BeanCopyUtils {
    private BeanCopyUtils() {
    }

    public static <T> T copyBean(Object o, Class<T> t) {
        //创建对象
        T t1 = null;
        try {
            t1 = t.newInstance();
            //实现属性copy
            BeanUtils.copyProperties(o, t1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回
        return t1;
    }

    public static <T, V> List<V> copyBeanList(List<T> o, Class<V> c) {
        return o.stream().map(p -> copyBean(p,c))
                .collect(Collectors.toList());
    }
}

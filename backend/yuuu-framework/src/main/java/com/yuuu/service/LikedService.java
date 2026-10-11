package com.yuuu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.entity.Liked;


/**
 * (Liked)表服务接口
 *
 * @author makejava
 * @since 2023-08-17 11:47:21
 */
public interface LikedService extends IService<Liked> {
    ResponseResult articleLike(Long id);

    ResponseResult commentLike(Long id);


    ResponseResult getArticleLike(Long id);

    ResponseResult getCommentLike(Long[] ids);
}

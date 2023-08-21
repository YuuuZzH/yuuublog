package com.yuuu.domain.entity;


import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Liked)表实体类
 *
 * @author makejava
 * @since 2023-08-17 11:44:32
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("yuuu_liked")
public class Liked  {
    @TableId(type = IdType.AUTO)
    private Long id;
    //文章id
    private Long articleId;
    //用户id
    private Long userId;
    //文章是否点赞，1点赞，0未点赞
    private Integer isLikeA = 0;
    //评论id
    private Long commentId;
    //评论是否点赞，1点赞，0未点赞
    private Integer isLikeC = 0;



}

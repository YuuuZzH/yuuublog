package com.yuuu.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (MessageBoard)表实体类
 *
 * @author makejava
 * @since 2024-01-26 14:58:19
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("message_board")
public class MessageBoard  {
    @TableId
    private Long id;

    //用户名
    private String userName;
    //头像
    private String avatar;
    //留言内容
    private String message;



}

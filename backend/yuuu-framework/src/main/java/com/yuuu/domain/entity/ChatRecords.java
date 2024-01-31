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
 * (ChatRecords)表实体类
 *
 * @author makejava
 * @since 2024-01-30 17:17:23
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("chat_records")
public class ChatRecords  {
    @TableId
    private Long id;

    //用户id
    @TableField("userId")
    private Long userId;
    //会话id
    @TableField("conversationId")
    private String conversationId;
    //提问
    @TableField("query")
    private String query;
    //回答
    @TableField("answer")
    private String answer;
    //聊天时间戳
    @TableField("timeStamp")
    private Long timeStamp;



}

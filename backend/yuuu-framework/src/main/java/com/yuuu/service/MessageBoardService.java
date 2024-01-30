package com.yuuu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.entity.MessageBoard;


/**
 * (MessageBoard)表服务接口
 *
 * @author makejava
 * @since 2024-01-26 14:58:19
 */
public interface MessageBoardService extends IService<MessageBoard> {

    ResponseResult saveMessage(String message);
}

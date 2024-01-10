package com.yuuu.service;

import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.dto.ChatDto;

/**
 * @author: yuuu
 * Description:
 */
public interface ChatService {
    ResponseResult chatMessage(ChatDto dto);

    ResponseResult getAllMessages();

}

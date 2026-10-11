package com.yuuu.controller;

import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.dto.ChatDto;
import com.yuuu.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: yuuu
 * Description: 聊天机器人
 */
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Resource
    private ChatService chatService;

    /**
     * @description: 聊天对话消息
     * @author: yuuu
     * @param: [query]
     * @return: com.yuuu.domain.ResponseResult
     **/
    @PostMapping("/chat-message")
    public ResponseResult chatMessage(@RequestBody ChatDto dto){
        return chatService.chatMessage(dto);
    }

    /**
     * @description: 获取用户所有的聊天记录
     * @author: yuuu
     * @param: []
     * @return: com.yuuu.domain.ResponseResult
     **/
    @GetMapping("/messages")
    public ResponseResult getAllMessages(){
        return chatService.getAllMessages();
    }
}

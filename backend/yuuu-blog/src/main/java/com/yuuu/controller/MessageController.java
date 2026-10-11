package com.yuuu.controller;

import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.dto.LeaveMessageDto;
import com.yuuu.domain.entity.MessageBoard;
import com.yuuu.service.MessageBoardService;
import com.yuuu.service.UserService;
import com.yuuu.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: yuuu
 * Description: 留言板 Controller
 */
@RestController
@RequestMapping("message")
public class MessageController {

    @Autowired
    private MessageBoardService service;

    @Autowired
    private UserService userService;

    @PostMapping("/leaveMessages")
    public ResponseResult saveMessage(@RequestBody LeaveMessageDto dto){
        return service.saveMessage(dto.getMessage());
    }

    @GetMapping("/list")
    public ResponseResult getMessages(){
        List<MessageBoard> list = service.list();
        return ResponseResult.okResult(list);
    }
}

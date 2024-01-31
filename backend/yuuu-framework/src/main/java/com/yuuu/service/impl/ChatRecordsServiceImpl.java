package com.yuuu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuuu.domain.entity.ChatRecords;
import com.yuuu.mapper.ChatRecordsMapper;
import com.yuuu.service.ChatRecordsService;
import org.springframework.stereotype.Service;

/**
 * (ChatRecords)表服务实现类
 *
 * @author makejava
 * @since 2024-01-30 17:17:23
 */
@Service("chatRecordsService")
public class ChatRecordsServiceImpl extends ServiceImpl<ChatRecordsMapper, ChatRecords> implements ChatRecordsService {

}

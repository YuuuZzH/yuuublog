package com.yuuu.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yuuu.constants.SystemConstants;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.dto.ChatDto;
import com.yuuu.domain.entity.ChatRecords;
import com.yuuu.domain.entity.LoginUser;
import com.yuuu.domain.mongo.ChatRecord;
import com.yuuu.domain.vo.ChatRecordVo;
import com.yuuu.enums.AppHttpCodeEnum;
import com.yuuu.exception.SystemException;
import com.yuuu.service.ChatRecordsService;
import com.yuuu.service.ChatService;
import com.yuuu.service.UserService;
import com.yuuu.utils.BeanCopyUtils;
import com.yuuu.utils.RedisCache;
import com.yuuu.utils.SecurityUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.yuuu.constants.ApiUrlConstants.DIFY;
import static com.yuuu.constants.ApiUrlConstants.DIFY_CHAT;

/**
 * @author: yuuu
 * Description:
 */
@Service
@ConfigurationProperties(prefix = "dify")
@Data
public class ChatServiceImpl implements ChatService {

    private String apiKey;
    private static final String API_URL = DIFY.getUrl();

    @Autowired
    private RedisTemplate redisTemplate;

    private final RestTemplate restTemplate;

    private final static int LIMIT = 5;


    public ChatServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseResult chatMessage(ChatDto dto) {
        Long userId = SecurityUtils.getUserId();
        String avatar = SecurityUtils.getLoginUser().getUser().getAvatar();
        // 计算距离明天零点的秒数
        long secondsUntilMidnight = Duration.between(
                Instant.now(),
                LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).atZone(ZoneId.of("Asia/Shanghai")).toInstant()
        ).get(ChronoUnit.SECONDS);
        Long messageCount = redisTemplate.opsForValue().increment(SystemConstants.BLOG_CHAT_COUNTER + userId, 1);
        redisTemplate.expire(SystemConstants.BLOG_CHAT_COUNTER + userId, secondsUntilMidnight, TimeUnit.SECONDS);
        if (messageCount > LIMIT) {
            ChatRecordVo chatRecordVo = new ChatRecordVo();
            chatRecordVo.setAnswer("啊哦，今天聊天太多了，请明天再来吧！");
            return ResponseResult.okResult(chatRecordVo);
        }
        List<ChatRecords> chatRecords = queryRecords(userId);
        String conversationId = "";
        if (chatRecords != null && chatRecords.size() > 0) {
            conversationId = chatRecords.get(0).getConversationId();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("User-Agent", "Mozilla/5.0");
        headers.set("Connection", "keep-alive");
        HttpEntity<String> requestEntity = new HttpEntity<>("{\n" +
                "    \"inputs\": {},\n" +
                "    \"query\": \"" + dto.getQuestion() + "\",\n" +
                "    \"response_mode\": \"blocking\",\n" +
                "    \"conversation_id\": \"" + conversationId + "\",\n" +
                "    \"user\": \"" + SystemConstants.BLOG_CHAT_KEY + userId + "\"\n" +
                "}", headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(API_URL + DIFY_CHAT.getUrl(),
                DIFY_CHAT.getMethod(), requestEntity, String.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String body = responseEntity.getBody();
            Map map = JSON.parseObject(body, Map.class);
            saveRecord(userId, map.get("conversation_id").toString(), dto.getQuestion(), map.get("answer").toString());
            ChatRecordVo chatRecordVo = new ChatRecordVo();
            chatRecordVo.setAnswer(map.get("answer").toString());
            chatRecordVo.setQuery(dto.getQuestion());
            chatRecordVo.setAvatar(avatar);
            chatRecordVo.setChatTimeStamp(new Date().getTime());
            return ResponseResult.okResult(chatRecordVo);
        } else {
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public ResponseResult getAllMessages() {
        Long userId = SecurityUtils.getUserId();
        String avatar = SecurityUtils.getLoginUser().getUser().getAvatar();
        List<ChatRecords> chatRecords = queryRecords(userId);
        List<ChatRecordVo> result = new ArrayList<>();
        for (ChatRecords chatRecord : chatRecords) {
            ChatRecordVo chatRecordVo = BeanCopyUtils.copyBean(chatRecord, ChatRecordVo.class);
            chatRecordVo.setAvatar(avatar);
            result.add(chatRecordVo);
        }
        return ResponseResult.okResult(result);
    }

    @Autowired
    private ChatRecordsService chatRecordsService;

    private void saveRecord(Long userId,
                            String conversationId,
                            String query,
                            String answer) {
        ChatRecords chatRecord = new ChatRecords();
        chatRecord.setUserId(userId);
        chatRecord.setConversationId(conversationId);
        chatRecord.setQuery(query);
        chatRecord.setAnswer(answer);
        chatRecord.setTimeStamp(System.currentTimeMillis());
        chatRecordsService.save(chatRecord);
    }

    private List<ChatRecords> queryRecords(Long userId) {
        LambdaQueryWrapper<ChatRecords> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatRecords::getUserId,userId).orderByAsc(ChatRecords::getTimeStamp);
        return chatRecordsService.list(queryWrapper);
    }

    /*@Autowired
    private MongoTemplate mongoTemplate;


    *//**
     * @description: 保存聊天记录到mongo中
     * @author: yuuu
     * @param: [userId, conversationId, query, answer]
     * @return: void
     **//*
    private void saveRecord(String userId,
                            String conversationId,
                            String query,
                            String answer) {
        ChatRecord chatRecord = new ChatRecord();
        chatRecord.setUserId(userId);
        chatRecord.setConversationId(conversationId);
        chatRecord.setQuery(query);
        chatRecord.setAnswer(answer);
        chatRecord.setChatTimeStamp(System.currentTimeMillis());
        mongoTemplate.save(chatRecord);
    }

    *//**
     * @description: 根据userId获取聊天记录
     * @author: yuuu
     * @param: [userId]
     * @return: java.util.List<com.yuuu.domain.mongo.ChatRecord>
     **//*
    private List<ChatRecord> queryRecords(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId)).with(Sort.by(Sort.Order.asc("chatTimeStamp")));
        return mongoTemplate.find(query, ChatRecord.class);
    }*/
}

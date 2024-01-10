package com.yuuu.domain.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author: yuuu
 * Description: 机器人聊天记录
 */

@Data
@Document(value = "chatRecord")
@AllArgsConstructor
@NoArgsConstructor
public class ChatRecord {
    @Id
    private ObjectId id;
    @Indexed
    private String userId;
    private String conversationId;
    private String query;
    private String answer;
    private Long chatTimeStamp;
}

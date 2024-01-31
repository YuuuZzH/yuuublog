package com.yuuu.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: yuuu
 * Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRecordVo {
    private String query;
    private String answer;
    private String avatar;
    private Long chatTimeStamp;
}

package com.yuuu.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: yuuu
 * Description:用户信息改变
 * Email: ***REMOVED***
 * CreatedTime: 2023-05-19 18:00:18:00
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserChangeDto {
    private Long userId;
    private String status;
}

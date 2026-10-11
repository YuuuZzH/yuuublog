package com.yuuu.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: yuuu
 * Description: github第三方接入的实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GithubUser {
    private String login;
    private Integer id;
    private String nodeId;
    private String avatarUrl;
}

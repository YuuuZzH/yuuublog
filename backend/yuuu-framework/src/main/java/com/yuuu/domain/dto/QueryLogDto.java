package com.yuuu.domain.dto;

import com.yuuu.domain.entity.LoginConfig;
import lombok.Data;

/**
 * @author: yuuu
 * Description:
 */
@Data
public class QueryLogDto extends LoginConfig {
    private Integer pageNo = 1;
    private Integer pageSize = 10;
    /**
     * 时间选项 1 ： 近 7天  2 ：近30天
     */
    private Integer nearly = 1;
}

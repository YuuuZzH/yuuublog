package com.yuuu.domain.dto;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SensitiveDto {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private String sensitives;
    @DateTimeFormat("yyyy-MM-dd")
    private String createdTime;
}

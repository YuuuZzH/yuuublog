package com.yuuu.controller;

import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.dto.QueryLogDto;
import com.yuuu.enums.AppHttpCodeEnum;
import com.yuuu.exception.SystemException;
import com.yuuu.service.LoginConfigService;
import com.yuuu.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: yuuu
 * Description:
 */
@RestController
@RequestMapping("/log")
public class VisitorLogController {

    @Autowired
    private LoginConfigService service;

    @PostMapping("/loginLog")
    public ResponseResult logByLogin(@RequestBody QueryLogDto dto){
        return service.queryLogByLogin(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteLoginLog(@PathVariable("id") List<Long> ids){
        if (!SecurityUtils.isAdmin()){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return service.deleteLoginLog(ids);
    }


}

package com.yuuu.controller;

import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.entity.resource.SystemHardwareInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: yuuu
 * Description:
 */
@RestController
@RequestMapping("system/monitor")
public class SystemResourceController {

    @GetMapping("/server")
    public ResponseResult getSystemInfo() throws Exception {
        SystemHardwareInfo systemHardwareInfo = new SystemHardwareInfo();
        systemHardwareInfo.copyTo();
        return ResponseResult.okResult(systemHardwareInfo);
    }

}

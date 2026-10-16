package com.yuuu.controller;

import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.entity.About;
import com.yuuu.service.AboutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 关于我信息Controller
 */
@RestController
@RequestMapping("/content/about")
public class AboutController {

    @Autowired
    private AboutService aboutService;

    /**
     * 获取关于我配置
     */
    @PreAuthorize("@ps.hasPermission('content:about:list')")
    @GetMapping("/get")
    public ResponseResult get() {
        return aboutService.getAbout();
    }

    /**
     * 保存关于我配置
     */
    @PreAuthorize("@ps.hasPermission('content:about:edit')")
    @PutMapping("/save")
    public ResponseResult save(@RequestBody About about) {
        return aboutService.saveAbout(about);
    }
}

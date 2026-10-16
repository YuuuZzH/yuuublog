package com.yuuu.controller;

import com.yuuu.domain.ResponseResult;
import com.yuuu.service.AboutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 关于我（前台匿名接口）
 */
@RestController
@RequestMapping("/about")
public class AboutController {

    @Autowired
    private AboutService aboutService;

    @GetMapping("/getAbout")
    public ResponseResult getAbout() {
        return aboutService.getAboutForFront();
    }
}

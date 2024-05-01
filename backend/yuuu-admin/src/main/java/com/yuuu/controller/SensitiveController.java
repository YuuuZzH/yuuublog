package com.yuuu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.dto.SensitiveDto;
import com.yuuu.domain.entity.Sensitive;
import com.yuuu.enums.AppHttpCodeEnum;
import com.yuuu.service.SensitiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

/**
 * 敏感词信息Controller
 *
 * @author ruoyi
 * @date 2024-04-30
 */
@RestController
@RequestMapping("/system/sensitive")
public class SensitiveController {
    @Autowired
    private SensitiveService sensitiveService;

    /**
     * 查询敏感词信息列表
     */
    @PreAuthorize("@ps.hasPermission('system:sensitive:list')")
    @GetMapping("/list")
    public ResponseResult list(SensitiveDto sensitive) {
        return sensitiveService.selectSensitiveList(sensitive);
    }

    /**
     * 获取敏感词信息详细信息
     */
    @PreAuthorize("@ps.hasPermission('system:sensitive:query')")
    @GetMapping(value = "/{id}")
    public ResponseResult getInfo(@PathVariable("id") String id) {
        return ResponseResult.okResult(sensitiveService.getById(id));
    }

    /**
     * 新增敏感词信息
     */
    @PreAuthorize("@ps.hasPermission('system:sensitive:add')")
    @PostMapping
    public ResponseResult add(@RequestBody Sensitive sensitive) {
        int count = sensitiveService.count(new LambdaQueryWrapper<Sensitive>()
                .eq(Sensitive::getSensitives, sensitive.getSensitives()));
        if (count > 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NOT_UNIQUE);
        }
        return ResponseResult.okResult(sensitiveService.save(sensitive));
    }

    /**
     * 修改敏感词信息
     */
    @PreAuthorize("@ps.hasPermission('system:sensitive:edit')")
    @PutMapping
    public ResponseResult edit(@RequestBody Sensitive sensitive) {
        return ResponseResult.okResult(sensitiveService.updateById(sensitive));
    }

    /**
     * 删除敏感词信息
     */
    @PreAuthorize("@ps.hasPermission('system:sensitive:remove')")
    @DeleteMapping("/{ids}")
    public ResponseResult remove(@PathVariable Long[] ids) {
        for (Long id : ids) {
            sensitiveService.removeById(id);
        }
        return ResponseResult.okResult();
    }
}

package com.yuuu.controller;

import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.dto.MenuListDto;
import com.yuuu.domain.entity.Menu;
import com.yuuu.enums.AppHttpCodeEnum;
import com.yuuu.exception.SystemException;
import com.yuuu.service.MenuService;
import com.yuuu.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @BelongsProject:YuuuBlog
 * @BelongsPackage:com.yuuu.controller
 * @Author:Yuuu。
 * @CreateTime:2023-03-17 13:59
 * @Description:
 * @Version:1.0
 */
@RestController
@RequestMapping("/system/menu")
public class SystemController {
    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('system:menu:list')")
    public ResponseResult menuList(MenuListDto menuListDto){
        return menuService.menuList(menuListDto);
    }

    @PostMapping
    @PreAuthorize("@ps.hasPermission('system:menu:add')")
    public ResponseResult addMenu(@RequestBody Menu menu){
        if (!SecurityUtils.isAdmin()){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return menuService.addMenu(menu);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('system:menu:query')")
    public ResponseResult getMenuById(@PathVariable("id") Long id){
        return menuService.getMenuById(id);
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('system:menu:edit')")
    public ResponseResult updateMenu(@RequestBody Menu menu){
        if (!SecurityUtils.isAdmin()){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return menuService.updateMenu(menu);
    }

    @DeleteMapping("/{menuId}")
    @PreAuthorize("@ps.hasPermission('system:menu:remove')")
    public ResponseResult deleteMenu(@PathVariable("menuId") Long menuId){
        if (!SecurityUtils.isAdmin()){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return menuService.deleteMenu(menuId);
    }

    @GetMapping("/treeselect")
    @PreAuthorize("@ps.hasPermission('system:menu:list')")
    public ResponseResult treeSelect(){
        return menuService.treeSelect();
    }

    @GetMapping("/roleMenuTreeselect/{id}")
    @PreAuthorize("@ps.hasPermission('system:menu:list')")
    public ResponseResult roleMenuTreeselectById(@PathVariable("id") Long id){
        return menuService.roleMenuTreeselectById(id);
    }
}

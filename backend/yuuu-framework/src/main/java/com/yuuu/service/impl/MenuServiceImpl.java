package com.yuuu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuuu.domain.entity.RoleMenu;
import com.yuuu.domain.vo.MenuUpdateVo;
import com.yuuu.domain.vo.TreeVo;
import com.yuuu.service.MenuService;
import com.yuuu.constants.SystemConstants;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.dto.MenuListDto;
import com.yuuu.domain.entity.Menu;
import com.yuuu.domain.vo.MenuByIdVo;
import com.yuuu.domain.vo.MenuListVo;
import com.yuuu.enums.AppHttpCodeEnum;
import com.yuuu.mapper.MenuMapper;
import com.yuuu.service.RoleMenuService;
import com.yuuu.utils.BeanCopyUtils;
import com.yuuu.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-03-08 19:11:33
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private MenuService menuService;

    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果是管理员，返回所有的权限
        if (SecurityUtils.isAdmin()){
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(Menu::getMenuType, SystemConstants.MENU,SystemConstants.BUTTON);
            queryWrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> list = list(queryWrapper);
            List<String> perms = list.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        //否则返回用户所具有的权限
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus  = null;
        //当前用户为管理员，则返回所有的menu
        if (SecurityUtils.isAdmin()){
            menus = menuMapper.selectAllRouterMenu();
        }else {
            //不是则返回用户所具有的menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        //构建父子tree
        List<Menu> tree = buildMenuTree(menus,0L);

        return tree;
    }

    @Override
    public ResponseResult menuList(MenuListDto menuListDto) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(menuListDto.getMenuName())){
            queryWrapper.like(Menu::getMenuName,menuListDto.getMenuName());
        }
        if (StringUtils.hasText(menuListDto.getStatus())){
            queryWrapper.eq(Menu::getStatus,menuListDto.getStatus());
        }
        queryWrapper.orderByAsc(Menu::getParentId,Menu::getOrderNum);
        List<Menu> menus = list(queryWrapper);
        List<MenuListVo> menuListVos = BeanCopyUtils.copyBeanList(menus, MenuListVo.class);
        return ResponseResult.okResult(menuListVos);
    }

    @Override
    public ResponseResult addMenu(Menu menu) {
        Long userId = SecurityUtils.getUserId();
        menu.setCreateBy(userId);
        menu.setCreateTime(new Date());
        menu.setUpdateBy(userId);
        menu.setUpdateTime(new Date());
        boolean save = save(menu);
        if (save){
            return ResponseResult.okResult();
        }else{
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public ResponseResult getMenuById(Long id) {
        Menu menu = getById(id);
        MenuByIdVo menuByIdVo = BeanCopyUtils.copyBean(menu, MenuByIdVo.class);
        return ResponseResult.okResult(menuByIdVo);
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {
        Long userId = SecurityUtils.getUserId();
        menu.setUpdateBy(userId);
        menu.setUpdateTime(new Date());
        //上级菜单不能选择自己
        if (menu.getId().equals(menu.getParentId())){
            return ResponseResult.okResult(500,"修改菜单失败，上级菜单不能选择自己");
        }
        boolean b = updateById(menu);
        if (b){
            return ResponseResult.okResult();
        }else{
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public ResponseResult deleteMenu(Long menuId) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId,menuId);
        List<Menu> list = list(queryWrapper);
        if (!list.isEmpty()){
            return ResponseResult.errorResult(500,"存在子菜单，不允许删除");
        }
        removeById(menuId);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult treeSelect() {
        //获取菜单树
        List<Menu> list = list();
        List<TreeVo> treeVos = list.stream()
                .map(menu -> {
                    TreeVo treeVo = new TreeVo();
                    treeVo.setId(menu.getId());
                    treeVo.setLabel(menu.getMenuName());
                    treeVo.setParentId(menu.getParentId());
                    return treeVo;
                })
                .collect(Collectors.toList());

        List<TreeVo> treeVoList = buildMenuTree(treeVos);
        return ResponseResult.okResult(treeVoList);
    }

    private <T extends TreeVo> List<T> buildMenuTree(List<T> menuList){
        return menuList.stream()
                //过滤出父菜单
                .filter(menuVo -> Objects.equals(menuVo.getParentId(), 0L))
                //把该结点作为父结点，查找该结点的子结点并赋值
                .peek(menuVo -> menuVo.setChildren(getChildren(menuVo.getId(), menuList)))
                //返回结果
                .collect(Collectors.toList());
    }

    private <T extends TreeVo> List<TreeVo> getChildren(Long id, List<T> menuList) {
        return menuList.stream()
                //过滤出该父节点的子节点
                .filter(menuVo -> Objects.equals(menuVo.getParentId(), id))
                //把该子结点作为父结点，递归查找出该子节点的的子结点并赋值
                .peek(childrenMenuVo -> childrenMenuVo.setChildren(getChildren(childrenMenuVo.getId(), menuList)))
                .collect(Collectors.toList());
    }


    @Override
    public ResponseResult roleMenuTreeselectById(Long id) {
//        //根据roleId获取menuIds
//        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(RoleMenu::getRoleId,id);
//        List<Long> menuIds = roleMenuService.list(queryWrapper).stream()
//                .map(RoleMenu::getMenuId)
//                .collect(Collectors.toList());
//        //根据menuIds找到所有的menu
//        LambdaQueryWrapper<Menu> menuWrapper = new LambdaQueryWrapper<>();
//        menuWrapper.in(Menu::getId,menuIds);
//        List<Menu> menus = menuService.list(menuWrapper);
//        //构建menuTree
//        List<TreeVo> treeVos = menus.stream()
//                .map(menu -> {
//                    TreeVo treeVo = new TreeVo();
//                    treeVo.setId(menu.getId());
//                    treeVo.setLabel(menu.getMenuName());
//                    treeVo.setParentId(menu.getParentId());
//                    return treeVo;
//                }).collect(Collectors.toList());
//        List<TreeVo> menuTree = buildMenuTree(treeVos);
        List<Menu> list = list();
        List<TreeVo> treeVos = list.stream()
                .map(menu -> {
                    TreeVo treeVo = new TreeVo();
                    treeVo.setId(menu.getId());
                    treeVo.setLabel(menu.getMenuName());
                    treeVo.setParentId(menu.getParentId());
                    return treeVo;
                })
                .collect(Collectors.toList());

        List<TreeVo> treeVoList = buildMenuTree(treeVos);
        //角色所关联的菜单权限列表
        LambdaQueryWrapper<RoleMenu> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(RoleMenu::getRoleId,id);
        List<Long> longs = roleMenuService.list(queryWrapper1).stream()
                .map(RoleMenu::getMenuId)
                .collect(Collectors.toList());
        MenuUpdateVo menuUpdateVo = new MenuUpdateVo(treeVoList, longs);
        return ResponseResult.okResult(menuUpdateVo);
    }

    /**
    * @description:构建MenuTree
    * @author: Yuuu。
    * @date: 2023-03-13 21:33
    * @param: [menus, parentId]
    * @return: java.util.List<com.yuuu.domain.entity.Menu>
    **/
    private List<Menu> buildMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
    * @description:获取参数的子Menu集合
    * @author: Yuuu。
    * @date: 2023-03-13 21:33
    * @param: [menu, menus]
    * @return: java.util.List<com.yuuu.domain.entity.Menu>
    **/
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m -> m.setChildren(getChildren(m,menus)))
                .collect(Collectors.toList());
        return childrenList;
    }
}


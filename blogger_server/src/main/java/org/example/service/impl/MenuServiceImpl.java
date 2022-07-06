package org.example.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.dto.MenuSave;
import org.example.dto.MenuUpdate;
import org.example.entity.Menu;
import org.example.entity.RoleMenu;
import org.example.mapper.MenuMapper;
import org.example.service.MenuService;
import org.example.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author 张鹏
 * @since 2022-06-18
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private RoleMenuService roleMenuService;

    /**
     * 新增
     *
     * @param menuSave
     */
    @Override
    public void save(MenuSave menuSave) {
        // 查询是否存在父级ID
        Menu parentMenu = getById(menuSave.getParentId());
        Assert.notNull(parentMenu, "父级菜单不存在");
        Menu menu = BeanUtil.toBean(menuSave, Menu.class);
        save(menu);
    }


    /**
     * 菜单树
     *
     * @return
     */
    @Override
    public List<Tree<Long>> tree() {
        List<Menu> list = list();
        // 构建node列表
        return TreeUtil.build(list.stream().map(menu -> {
            TreeNode<Long> node = new TreeNode<>();
            node.setId(menu.getId());
            node.setName(menu.getName());
            node.setParentId(menu.getParentId());
            node.setWeight(menu.getWeight());
            Map<String, Object> map = new HashMap<>();
            map.put("path", menu.getPath());
            map.put("type", menu.getType());
            map.put("icon", menu.getIcon());
            node.setExtra(map);
            return node;
        }).collect(Collectors.toList()), 0L);
    }

    /**
     * 删除
     *
     * @param menuId
     */
    @Override
    public void delete(Long menuId) {
        // 判断是否存在子级
        long count = count(Wrappers.<Menu>lambdaQuery().eq(Menu::getParentId, menuId));
        Assert.isFalse(count > 0, "存在子级菜单");
        // 删除角色菜单关联关系
        roleMenuService.remove(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getMenuId, menuId));
        // 删除
        removeById(menuId);
    }

    /**
     * 修改
     *
     * @param menuUpdate
     */
    @Override
    public void update(MenuUpdate menuUpdate) {
        // 判断是否存在父级
        long count = count(Wrappers.<Menu>lambdaQuery().eq(Menu::getId, menuUpdate.getId()));
        Assert.isFalse(count > 0, "父级菜单不存在");
        Menu menu = BeanUtil.toBean(menuUpdate, Menu.class);
        updateById(menu);
    }
}

package org.example.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.dto.MenuSave;
import org.example.dto.MenuUpdate;
import org.example.entity.Menu;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author 张鹏
 * @since 2022-06-18
 */
public interface MenuService extends IService<Menu> {

    /**
     * 新增
     *
     * @param menuSave
     */
    void save(MenuSave menuSave);

    /**
     * 菜单树
     *
     * @return
     */
    List<Tree<Long>> tree();


    /**
     * 删除
     *
     * @param menuId
     */
    void delete(Long menuId);

    /**
     * 修改
     *
     * @param menuUpdate
     */
    void update(MenuUpdate menuUpdate);
}

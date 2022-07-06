package org.example.controller;

import cn.hutool.core.lang.tree.Tree;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.example.common.R;
import org.example.dto.MenuSave;
import org.example.dto.MenuUpdate;
import org.example.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author 张鹏
 * @since 2022-06-18
 */
@Slf4j
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;


    @ApiOperation("新增")
    @PostMapping("/save")
    public R<String> save(@RequestBody MenuSave menuSave) {
        menuService.save(menuSave);
        return R.success();
    }


    @ApiOperation("菜单树")
    @GetMapping("/tree")
    public R<List<Tree<Long>>> tree() {
        return R.success(menuService.tree());
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete")
    public R<String> delete(@RequestParam("menuId") Long menuId) {
        menuService.delete(menuId);
        return R.success();
    }


    @ApiOperation("修改")
    @PutMapping("/update")
    public R<String> update(@RequestBody MenuUpdate menuUpdate) {
        menuService.update(menuUpdate);
        return R.success();
    }


}

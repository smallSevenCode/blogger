package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.common.PageAccept;
import org.example.common.PageResult;
import org.example.common.R;
import org.example.dto.RoleQuery;
import org.example.dto.RoleSave;
import org.example.dto.RoleUpdate;
import org.example.service.RoleService;
import org.example.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author 张鹏
 * @since 2022-06-18
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;


    @ApiOperation("分页")
    @PostMapping("/listPage")
    public R<PageResult<RoleVo>> listPage(@RequestBody PageAccept<RoleQuery> pageAccept) {
        return R.success(roleService.listPage(pageAccept));
    }


    @ApiOperation("新增")
    @PostMapping("/save")
    public R<String> save(@RequestBody RoleSave roleSave) {
        roleService.save(roleSave);
        return R.success();
    }


    @ApiOperation("删除")
    @DeleteMapping("/delete")
    public R<String> delete(@RequestParam("roleId") Long roleId) {
        roleService.delete(roleId);
        return R.success();
    }


    @ApiOperation("修改")
    @PutMapping("/update")
    public R<String> update(@RequestBody RoleUpdate roleUpdate) {
        roleService.update(roleUpdate);
        return R.success();
    }


    @ApiOperation("缓存")
    @GetMapping("/cache")
    public R<String> cache() {
        roleService.cache();
        return R.success();
    }


}

package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.common.Login;
import org.example.common.PageAccept;
import org.example.common.PageResult;
import org.example.common.R;
import org.example.dto.UserQuery;
import org.example.dto.UserSave;
import org.example.dto.UserUpdate;
import org.example.service.UserService;
import org.example.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 张鹏
 * @since 2022-06-18
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("验证码")
    @GetMapping("/code")
    public R<String> code() {
        return R.success(userService.code());
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public R<UserVo> login(@RequestBody @Valid Login login) {
        return R.success(userService.login(login));
    }


    @ApiOperation("分页")
    @PostMapping("/listPage")
    public R<PageResult<UserVo>> listPage(@RequestBody PageAccept<UserQuery> pageAccept) {
        return R.success(userService.listPage(pageAccept));
    }


    @ApiOperation("新增")
    @PostMapping("/save")
    public R<String> save(@RequestBody @Valid UserSave userSave) {
        userService.save(userSave);
        return R.success();
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete")
    public R<String> delete(@RequestParam("userId") Long userId) {
        userService.delete(userId);
        return R.success();
    }

    @ApiOperation("修改")
    @PutMapping("/update")
    public R<String> update(@RequestBody @Valid UserUpdate userUpdate) {
        userService.update(userUpdate);
        return R.success();
    }

}

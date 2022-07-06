package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.common.PageAccept;
import org.example.common.PageResult;
import org.example.common.R;
import org.example.dto.PathQuery;
import org.example.service.PathService;
import org.example.vo.PathVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 接口表 前端控制器
 * </p>
 *
 * @author 张鹏
 * @since 2022-06-19
 */
@Api(tags = "接口管理")
@RestController
@RequestMapping("/path")
public class PathController {

    @Autowired
    private PathService pathService;


    @ApiOperation("分页")
    @PostMapping("/listPage")
    public R<PageResult<PathVo>> listPage(@RequestBody PageAccept<PathQuery> pageAccept) {
        return R.success(pathService.listPage(pageAccept));
    }


    @ApiOperation("保存")
    @GetMapping("/save")
    public R<String> save(@RequestParam("url") String url, @RequestParam("token") String token) {
        pathService.save(url, token);
        return R.success();
    }


}

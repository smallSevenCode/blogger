package org.example.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.common.PageAccept;
import org.example.common.PageResult;
import org.example.dto.RoleQuery;
import org.example.dto.RoleSave;
import org.example.dto.RoleUpdate;
import org.example.entity.Path;
import org.example.entity.Role;
import org.example.entity.RoleMenu;
import org.example.entity.RolePath;
import org.example.mapper.RoleMapper;
import org.example.redis.client.RedisClient;
import org.example.service.PathService;
import org.example.service.RoleMenuService;
import org.example.service.RolePathService;
import org.example.service.RoleService;
import org.example.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author 张鹏
 * @since 2022-06-18
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private RolePathService rolePathService;

    @Autowired
    private PathService pathService;

    @Autowired
    private RedisClient redisClient;


    /**
     * 分页
     *
     * @param pageAccept
     * @return
     */
    @Override
    public PageResult<RoleVo> listPage(PageAccept<RoleQuery> pageAccept) {
        RoleQuery params = pageAccept.getParams();
        IPage<Role> buildPage = pageAccept.buildPage(Role.class);
        Wrapper<Role> wrapper = Wrappers.<Role>lambdaQuery()
                .like(StrUtil.isNotBlank(params.getName()), Role::getName, params.getName());
        IPage<Role> page = page(buildPage, wrapper);
        return pageAccept.buildPage(page, RoleVo.class);
    }

    /**
     * 新增
     *
     * @param roleSave
     */
    @Override
    public void save(RoleSave roleSave) {
        // 判断是否存在
        Role role = getOne(Wrappers.<Role>lambdaQuery().eq(Role::getName, roleSave.getName()));
        Assert.isNull(role, "角色已存在");
        // 储存
        Role newRole = BeanUtil.toBean(roleSave, Role.class);
        save(newRole);
        // 关联菜单
        List<RoleMenu> roleMenuList = roleSave.getMenuIdList().stream().map(menuId ->
                RoleMenu.builder()
                        .menuId(menuId)
                        .roleId(newRole.getId())
                        .build()
        ).collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenuList);
        // 关联接口
        List<RolePath> rolePathList = roleSave.getPathIdList().stream().map(pathId ->
                RolePath.builder()
                        .pathId(pathId)
                        .roleId(newRole.getId())
                        .build()
        ).collect(Collectors.toList());
        rolePathService.saveBatch(rolePathList);
    }

    /**
     * 删除
     *
     * @param roleId
     */
    @Override
    public void delete(Long roleId) {
        removeById(roleId);
        // 删除关联菜单信息
        roleMenuService.remove(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId, roleId));
    }

    /**
     * 修改
     *
     * @param roleUpdate
     */
    @Override
    public void update(RoleUpdate roleUpdate) {
        // 判断是否存在
        Role role = getOne(Wrappers.<Role>lambdaQuery().eq(Role::getName, roleUpdate.getName()).notIn(Role::getId, roleUpdate.getId()));
        Assert.isNull(role, "角色已存在");
        Role newRole = BeanUtil.toBean(roleUpdate, Role.class);
        updateById(newRole);
        // 关联菜单
        roleMenuService.remove(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId, newRole.getId()));
        List<RoleMenu> roleMenuList = roleUpdate.getMenuIdList().stream().map(menuId ->
                RoleMenu.builder()
                        .menuId(menuId)
                        .roleId(newRole.getId())
                        .build()
        ).collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenuList);
        // 关联接口
        rolePathService.remove(Wrappers.<RolePath>lambdaQuery().eq(RolePath::getRoleId, newRole.getId()));
        List<RolePath> rolePathList = roleUpdate.getPathIdList().stream().map(pathId ->
                RolePath.builder()
                        .pathId(pathId)
                        .roleId(newRole.getId())
                        .build()
        ).collect(Collectors.toList());
        rolePathService.saveBatch(rolePathList);
    }

    /**
     *
     */
    @Override
    public void cache() {
        // 获取所有角色
        List<Role> list = list();
        // 获取所有角色下的路径
        List<Long> roleIdList = list.stream().map(Role::getId).collect(Collectors.toList());
        List<RolePath> rolePathList = rolePathService.list(Wrappers.<RolePath>lambdaQuery().in(RolePath::getRoleId, roleIdList));
        // 角色路径分组
        Map<Long, List<RolePath>> roleMap = rolePathList.stream().collect(Collectors.groupingBy(RolePath::getRoleId));
        roleMap.forEach((roleId, pathList) -> {
            // 获取角色下的路径
            List<Long> pathIdList = pathList.stream().map(RolePath::getPathId).collect(Collectors.toList());
            List<Path> paths = pathService.listByIds(pathIdList);
            // 储存到redis中
            redisClient.del("security", roleId);
            Map<String, String> pathMap = paths.stream().collect(Collectors.toMap(Path::getUrl, Path::getUrl));
            redisClient.putAll(pathMap, "security", roleId);
        });
    }

}

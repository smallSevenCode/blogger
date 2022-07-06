package org.example.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.client.JwtClient;
import org.example.common.Login;
import org.example.common.PageAccept;
import org.example.common.PageResult;
import org.example.common.UserInfo;
import org.example.dto.UserQuery;
import org.example.dto.UserSave;
import org.example.dto.UserUpdate;
import org.example.entity.Menu;
import org.example.entity.RoleMenu;
import org.example.entity.User;
import org.example.entity.UserRole;
import org.example.mapper.UserMapper;
import org.example.redis.client.RedisClient;
import org.example.service.MenuService;
import org.example.service.RoleMenuService;
import org.example.service.UserRoleService;
import org.example.service.UserService;
import org.example.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 张鹏
 * @since 2022-06-18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private JwtClient jwtClient;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private MenuService menuService;


    /**
     * 验证码
     *
     * @return
     */
    @Override
    public String code() {
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(200, 100);
        String code = captcha.getCode();
        redisClient.setAndTime(code, 60, TimeUnit.SECONDS, "code", code);
        return captcha.getImageBase64Data();
    }

    /**
     * 登录
     *
     * @param login
     * @return
     */
    @Override
    public UserVo login(Login login) {
        Assert.isTrue(redisClient.isKey("code", login.getCode()), "验证码错误");
        // 查询用户
        User user = getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, login.getUsername()).eq(User::getIsDel, 0));
        Assert.notNull(user, "用户不存在");
        Assert.isTrue(BCrypt.checkpw(login.getPassword(), user.getPassword()), "密码错误");
        // 获取token
        UserInfo userInfo = BeanUtil.toBean(user, UserInfo.class);
        // 查询角色
        List<UserRole> list = userRoleService.list(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, userInfo.getId()));
        List<Long> roleIdList = list.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        userInfo.setRoleIdList(roleIdList);
        // 删除验证码
        redisClient.del("code", login.getCode());
        // 生成token
        String token = jwtClient.createToken(userInfo);
        // 获取菜单
        List<RoleMenu> roleMenuList = roleMenuService.list(Wrappers.<RoleMenu>lambdaQuery().in(RoleMenu::getRoleId, roleIdList));
        List<Long> menuIdList = roleMenuList.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        List<Menu> menuList = menuService.listByIds(menuIdList);
        // 格式化菜单
        List<Tree<Long>> treeList = TreeUtil.build(menuList.stream().map(menu -> {
            TreeNode<Long> node = new TreeNode<>();
            Map<String, Object> map = new HashMap<>();
            map.put("path", menu.getPath());
            map.put("icon", menu.getIcon());
            map.put("type", menu.getType());
            node.setExtra(map);
            node.setId(menu.getId());
            node.setName(menu.getName());
            node.setParentId(menu.getParentId());
            node.setWeight(menu.getWeight());
            return node;
        }).collect(Collectors.toList()), 0L);
        // 封装数据
        UserVo userVo = BeanUtil.toBean(user, UserVo.class);
        userVo.setToken(token);
        userVo.setMenuList(treeList);
        return userVo;
    }


    /**
     * 分页
     *
     * @param pageAccept
     * @return
     */
    @Override
    public PageResult<UserVo> listPage(PageAccept<UserQuery> pageAccept) {
        UserQuery params = pageAccept.getParams();
        IPage<User> buildPage = pageAccept.buildPage(User.class);
        Wrapper<User> wrapper = Wrappers.<User>lambdaQuery()
                .like(StrUtil.isNotBlank(params.getUsername()), User::getUsername, params.getUsername())
                .like(StrUtil.isNotBlank(params.getEmail()), User::getEmail, params.getEmail())
                .eq(ObjectUtil.isNotNull(params.getType()), User::getType, params.getType())
                .eq(User::getIsDel, 0);
        IPage<User> page = page(buildPage, wrapper);
        return pageAccept.buildPage(page, UserVo.class);
    }

    /**
     * 新增
     *
     * @param userSave
     */
    @Override
    public void save(UserSave userSave) {
        // 判断是否存在
        User user = getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, userSave.getUsername()));
        Assert.isNull(user, "用户已存在");
        user = getOne(Wrappers.<User>lambdaQuery().eq(User::getEmail, userSave.getEmail()));
        Assert.isNull(user, "邮箱已存在");
        // 储存
        User newUser = BeanUtil.toBean(userSave, User.class);
        newUser.setPassword(BCrypt.hashpw(newUser.getPassword()));
        newUser.setIsDel(0);
        save(newUser);
        // 角色
        List<UserRole> userRoleList = userSave.getRoleIdList().stream().map(roleId ->
                UserRole.builder()
                        .roleId(roleId)
                        .userId(newUser.getId())
                        .build()
        ).collect(Collectors.toList());
        userRoleService.saveBatch(userRoleList);
    }


    /**
     * 删除
     *
     * @param userId
     */
    @Override
    public void delete(Long userId) {
        User user = getById(userId);
        Assert.notNull(user, "用户不存在");
        Assert.isFalse(user.getIsDel().equals(1), "用户不存在");
        user.setIsDel(1);
        updateById(user);
    }

    /**
     * 修改
     *
     * @param userUpdate
     */
    @Override
    public void update(UserUpdate userUpdate) {
        // 判断是否存在
        User user = getOne(Wrappers.<User>lambdaQuery().notIn(User::getId, userUpdate.getId()).eq(User::getEmail, userUpdate.getEmail()));
        Assert.notNull(user, "邮箱已存在");
        user = getOne(Wrappers.<User>lambdaQuery().notIn(User::getId, userUpdate.getId()).eq(User::getUsername, userUpdate.getUsername()));
        Assert.notNull(user, "用户已存在");
        // 修改
        User newUser = BeanUtil.toBean(userUpdate, User.class);
        newUser.setPassword(BCrypt.hashpw(newUser.getPassword()));
        save(newUser);
        // 角色
        userRoleService.remove(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, newUser.getId()));
        List<UserRole> userRoleList = userUpdate.getRoleIdList().stream().map(roleId ->
                UserRole.builder()
                        .roleId(roleId)
                        .userId(newUser.getId())
                        .build()
        ).collect(Collectors.toList());
        userRoleService.saveBatch(userRoleList);

    }
}

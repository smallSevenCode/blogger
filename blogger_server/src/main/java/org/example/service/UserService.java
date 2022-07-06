package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.common.Login;
import org.example.common.PageAccept;
import org.example.common.PageResult;
import org.example.dto.UserQuery;
import org.example.dto.UserSave;
import org.example.dto.UserUpdate;
import org.example.entity.User;
import org.example.vo.UserVo;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 张鹏
 * @since 2022-06-18
 */
public interface UserService extends IService<User> {

    /**
     * 验证码
     *
     * @return
     */
    String code();

    /**
     * 登录
     *
     * @param login
     * @return
     */
    UserVo login(Login login);

    /**
     * 分页
     *
     * @param pageAccept
     * @return
     */
    PageResult<UserVo> listPage(PageAccept<UserQuery> pageAccept);

    /**
     * 新增
     *
     * @param userSave
     */
    void save(UserSave userSave);

    /**
     * 删除
     *
     * @param userId
     */
    void delete(Long userId);

    /**
     * 修改
     *
     * @param userUpdate
     */
    void update(UserUpdate userUpdate);


}

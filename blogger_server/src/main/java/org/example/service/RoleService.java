package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.common.PageAccept;
import org.example.common.PageResult;
import org.example.dto.RoleQuery;
import org.example.dto.RoleSave;
import org.example.dto.RoleUpdate;
import org.example.entity.Role;
import org.example.vo.RoleVo;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author 张鹏
 * @since 2022-06-18
 */
public interface RoleService extends IService<Role> {


    /**
     * 分页
     *
     * @param pageAccept
     * @return
     */
    PageResult<RoleVo> listPage(PageAccept<RoleQuery> pageAccept);

    /**
     * 新增
     *
     * @param roleSave
     */
    void save(RoleSave roleSave);

    /**
     * 删除
     *
     * @param roleId
     */
    void delete(Long roleId);

    /**
     * 修改
     *
     * @param roleUpdate
     */
    void update(RoleUpdate roleUpdate);

    /**
     * 缓存
     */
    void cache();


}

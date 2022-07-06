package org.example.mapper;

import org.example.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author 张鹏
 * @since 2022-06-18
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}

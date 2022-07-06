package org.example.mapper;

import org.example.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户角色表 Mapper 接口
 * </p>
 *
 * @author 张鹏
 * @since 2022-06-18
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}

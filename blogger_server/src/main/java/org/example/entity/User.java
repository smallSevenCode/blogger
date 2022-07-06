package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.example.common.ParentModel;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author 张鹏
 * @since 2022-06-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tb_user")
public class User extends ParentModel {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 头像
     */
    @TableField("head_icon")
    private String headIcon;

    /**
     * 用户类型 0.后台 1.前台
     */
    @TableField("type")
    private Integer type;

    /**
     * 是否删除 0.否 1.是
     */
    @TableField("is_del")
    private Integer isDel;


}

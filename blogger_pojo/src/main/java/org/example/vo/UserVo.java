package org.example.vo;

import cn.hutool.core.lang.tree.Tree;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.example.common.ParentModelVo;

import java.util.List;

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
@ApiModel("用户表")
public class UserVo extends ParentModelVo {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty("ID")
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String email;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private String headIcon;

    /**
     * 用户类型 0.后台 1.前台
     */
    @ApiModelProperty("用户类型 0.后台 1.前台")
    private Integer type;

    /**
     * token令牌
     */
    @ApiModelProperty("token令牌")
    private String token;

    /**
     * 菜单
     */
    @ApiModelProperty("菜单树")
    private List<Tree<Long>> menuList;


}

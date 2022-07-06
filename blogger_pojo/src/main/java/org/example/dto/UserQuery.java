package org.example.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
@ApiModel("用户表")
public class UserQuery implements Serializable {

    private static final long serialVersionUID = 1L;

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


}

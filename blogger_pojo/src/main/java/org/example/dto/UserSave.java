package org.example.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
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
@ApiModel("用户表")
public class UserSave implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱", required = true)
    @NotBlank(message = "邮箱不能为空")
    private String email;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像", required = true)
    @NotBlank(message = "头像不能为空")
    private String headIcon;

    /**
     * 用户类型 0.后台 1.前台
     */
    @ApiModelProperty(value = "用户类型 0.后台 1.前台", required = true)
    @NotNull(message = "用户类型不能为空")
    private Integer type;


    @ApiModelProperty(value = "角色ID", required = true)
    @NotEmpty(message = "角色不能为空")
    private List<Long> roleIdList;


}

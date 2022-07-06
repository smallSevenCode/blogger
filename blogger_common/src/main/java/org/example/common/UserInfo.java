package org.example.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 当前用户信息
 *
 * @author: 张鹏
 * @date: 2022/6/18 18:34
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户信息")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty("用户ID")
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;

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
     * 访问路径
     */
    @ApiModelProperty("访问路径")
    private String path;

    /**
     * 角色ID
     */
    @ApiModelProperty("角色ID")
    private List<Long> roleIdList;


}

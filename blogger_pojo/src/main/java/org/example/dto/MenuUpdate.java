package org.example.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author 张鹏
 * @since 2022-06-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("菜单表")
public class MenuUpdate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", required = true)
    @NotNull(message = "ID不能为空")
    private Long id;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称", required = true)
    @NotBlank(message = "菜单名称不能为空")
    private String name;

    /**
     * URL路径
     */
    @ApiModelProperty("URL路径")
    private String path;

    /**
     * 菜单类型 0.菜单 1.页面
     */
    @ApiModelProperty(value = "菜单类型 0.菜单 1.页面", required = true)
    @NotNull(message = "菜单类型不能为空")
    private Integer type;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标", required = true)
    @NotNull(message = "图标不能为空")
    private String icon;

    /**
     * 权重
     */
    @ApiModelProperty(value = "权重", required = true)
    @NotNull(message = "权重不能为空")
    private Integer weight;

    /**
     * 父级ID
     */
    @ApiModelProperty(value = "父级ID", required = true)
    @NotNull(message = "父级ID不能为空")
    private Long parentId;


}

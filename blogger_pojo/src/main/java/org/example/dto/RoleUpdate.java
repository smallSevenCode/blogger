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
 * 角色表
 * </p>
 *
 * @author 张鹏
 * @since 2022-06-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("角色表")
public class RoleUpdate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", required = true)
    @NotNull(message = "角色ID不能为空")
    private Long id;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称", required = true)
    @NotBlank(message = "角色名称不能为空")
    private String name;

    /**
     * 菜单ID
     */
    @ApiModelProperty(value = "菜单ID", required = true)
    @NotEmpty(message = "菜单ID不能为空")
    private List<Long> menuIdList;

    /**
     * 接口ID
     */
    @ApiModelProperty(value = "接口ID", required = true)
    @NotEmpty(message = "接口ID不能为空")
    private List<Long> pathIdList;


}

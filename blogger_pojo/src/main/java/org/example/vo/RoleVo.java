package org.example.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.example.common.ParentModelVo;

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
@EqualsAndHashCode(callSuper = true)
@ApiModel("角色表")
public class RoleVo extends ParentModelVo {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty("ID")
    private Long id;

    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    private String roleName;


}

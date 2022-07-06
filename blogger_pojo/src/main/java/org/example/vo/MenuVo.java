package org.example.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.example.common.ParentModelVo;

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
@EqualsAndHashCode(callSuper = true)
@ApiModel("菜单表")
public class MenuVo extends ParentModelVo {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty("ID")
    private Long id;

    /**
     * 菜单名称
     */
    @ApiModelProperty("菜单名称")
    private String menuName;

    /**
     * URL路径
     */
    @ApiModelProperty("URL路径")
    private String path;

    /**
     * 菜单类型 0.菜单 1.页面
     */
    @ApiModelProperty("菜单类型 0.菜单 1.页面")
    private Integer type;

    /**
     * 图标
     */
    @ApiModelProperty("图标")
    private String icon;

    /**
     * 权重
     */
    @ApiModelProperty("权重")
    private Integer weight;

    /**
     * 父级ID
     */
    @ApiModelProperty("父级ID")
    private Long parentId;


}

package org.example.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.example.common.ParentModelVo;

/**
 * <p>
 * 接口表
 * </p>
 *
 * @author 张鹏
 * @since 2022-06-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("接口表")
public class PathVo extends ParentModelVo {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty("ID")
    private Long id;

    /**
     * 分组
     */
    @ApiModelProperty("tags")
    private String tags;

    /**
     * 接口名称
     */
    @ApiModelProperty("name")
    private String name;

    /**
     * 请求方式
     */
    @ApiModelProperty("mode")
    private String mode;

    /**
     * 接口路径
     */
    @ApiModelProperty("url")
    private String url;


}

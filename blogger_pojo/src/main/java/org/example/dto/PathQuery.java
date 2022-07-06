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
@ApiModel("接口表")
public class PathQuery implements Serializable {

    private static final long serialVersionUID = 1L;


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

package org.example.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础实体
 *
 * @author: 张鹏
 * @date: 2022/1/4 20:34
 */
@Data
public class ParentModelVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;

    /**
     * 修改ID
     */
    @ApiModelProperty("修改ID")
    private Long updateId;

    /**
     * 修改名称
     */
    @ApiModelProperty("修改名称")
    private String updateName;

    /**
     * 新建时间
     */
    @ApiModelProperty("新建时间")
    private LocalDateTime createTime;

    /**
     * 新建ID
     */
    @ApiModelProperty("新建ID")
    private Long createId;

    /**
     * 新建名称
     */
    @ApiModelProperty("新建名称")
    private String createName;


}

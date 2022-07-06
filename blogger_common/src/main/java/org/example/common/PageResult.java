package org.example.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果集
 *
 * @author: 张鹏
 * @date: 2022/6/24 23:04
 **/
@Data
@ApiModel("分页结果集")
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("当前页")
    private long current;

    @ApiModelProperty("每页数量")
    private long size;

    @ApiModelProperty("总数量")
    private long total;

    @ApiModelProperty("结果集")
    private List<T> records;

}

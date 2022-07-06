package org.example.common;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页参数
 *
 * @author: 张鹏
 * @date: 2022/6/24 23:00
 **/
@Data
@ApiModel("分页参数")
public class PageAccept<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("当前页")
    private long current = 1;

    @ApiModelProperty("每页数量")
    private long size = 10;

    @ApiModelProperty("实体参数")
    private T params;


    public <K> IPage<K> buildPage(Class<K> tClass) {
        IPage<K> page = new Page<>();
        page.setCurrent(this.current);
        page.setSize(this.size);
        return page;
    }


    public <V, K> PageResult<V> buildPage(IPage<K> page, Class<V> tClass) {
        PageResult<V> result = new PageResult<>();
        result.setCurrent(page.getCurrent());
        result.setSize(page.getSize());
        result.setTotal(page.getTotal());
        result.setRecords(BeanUtil.copyToList(page.getRecords(), tClass));
        return result;
    }


}

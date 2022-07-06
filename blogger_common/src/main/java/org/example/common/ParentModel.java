package org.example.common;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
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
public class ParentModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 修改ID
     */
    @TableField(value = "update_id", fill = FieldFill.INSERT_UPDATE)
    private Long updateId;

    /**
     * 修改名称
     */
    @TableField(value = "update_name", fill = FieldFill.INSERT_UPDATE)
    private String updateName;

    /**
     * 新建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 新建ID
     */
    @TableField(value = "create_id", fill = FieldFill.INSERT)
    private Long createId;

    /**
     * 新建名称
     */
    @TableField(value = "create_name", fill = FieldFill.INSERT)
    private String createName;


}

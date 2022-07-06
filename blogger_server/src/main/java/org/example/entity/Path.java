package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.example.common.ParentModel;

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
@TableName("tb_path")
public class Path extends ParentModel {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 分组
     */
    @TableField("tags")
    private String tags;

    /**
     * 接口名称
     */
    @TableField("name")
    private String name;

    /**
     * 请求方式
     */
    @TableField("mode")
    private String mode;

    /**
     * 接口路径
     */
    @TableField("url")
    private String url;


}

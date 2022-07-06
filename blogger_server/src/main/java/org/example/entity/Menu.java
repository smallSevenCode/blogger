package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.example.common.ParentModel;

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
@TableName("tb_menu")
public class Menu extends ParentModel {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 菜单名称
     */
    @TableField("name")
    private String name;

    /**
     * URL路径
     */
    @TableField("path")
    private String path;

    /**
     * 菜单类型 0.菜单 1.页面
     */
    @TableField("type")
    private Integer type;

    /**
     * 图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 权重
     */
    @TableField("weight")
    private Integer weight;

    /**
     * 父级ID
     */
    @TableField("parent_id")
    private Long parentId;


}

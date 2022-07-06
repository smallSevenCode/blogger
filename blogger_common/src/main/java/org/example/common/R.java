package org.example.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应
 *
 * @author: 张鹏
 * @date: 2022/1/4 20:51
 **/
@Data
@ApiModel("响应参数")
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    @ApiModelProperty("状态码")
    private int code;
    /**
     * 状态信息
     */
    @ApiModelProperty("状态信息")
    private String message;
    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private boolean status;
    /**
     * 结果集
     */
    @ApiModelProperty("结果集")
    private T result;
    /**
     * 时间戳
     */
    @ApiModelProperty("时间戳")
    private long timestamp;


    public R(int code, String message, boolean status, T result) {
        this.code = code;
        this.message = message;
        this.status = status;
        this.result = result;
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> R<T> success() {
        return new R<>(0, "成功", true, null);
    }

    public static <T> R<T> success(T result) {
        return new R<>(0, "成功", true, result);
    }

    public static <T> R<T> success(T result, String message) {
        return new R<>(0, message, true, result);
    }

    public static <T> R<T> success(T result, String message, int code) {
        return new R<>(code, message, true, result);
    }

    public static <T> R<T> failed() {
        return new R<>(-1, "失败", false, null);
    }

    public static <T> R<T> failed(String message) {
        return new R<>(-1, message, false, null);
    }

    public static <T> R<T> failed(String message, int code) {
        return new R<>(code, message, false, null);
    }
}

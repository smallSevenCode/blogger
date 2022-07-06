package org.example.config;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.example.common.UserInfo;
import org.example.utils.UserInfoUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * mybatis-plus配置
 *
 * @author: 张鹏
 * @date: 2022/1/4 20:34
 */
@Configuration
public class MybatisPlusConfig {

    private static final String create_id = "createId";
    private static final String create_name = "createName";
    private static final String create_time = "createTime";
    private static final String update_id = "updateId";
    private static final String update_name = "updateName";
    private static final String update_time = "updateTime";

    /**
     * 分页插件
     *
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor innerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        innerInterceptor.setMaxLimit(100L);
        innerInterceptor.setOverflow(true);
        innerInterceptor.setOptimizeJoin(true);
        interceptor.addInnerInterceptor(innerInterceptor);
        return interceptor;
    }


    /**
     * 重写自动填充策略
     *
     * @return
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            /**
             * 新增填充策略
             * @param metaObject
             */
            @Override
            public void insertFill(MetaObject metaObject) {
                insertAndUpdate(metaObject, Arrays.asList(create_id, create_name, create_time, update_id, update_name, update_time));
            }

            /**
             * 修改填充策略
             * @param metaObject
             */
            @Override
            public void updateFill(MetaObject metaObject) {
                insertAndUpdate(metaObject, Arrays.asList(update_id, update_name, update_time));
            }
        };
    }

    private void insertAndUpdate(MetaObject metaObject, List<String> fillNameList) {
        // 从隔离线程中获取用户信息
        UserInfo userInfo = UserInfoUtil.get();
        if (ObjectUtil.isNull(userInfo)) {
            userInfo = new UserInfo();
        }
        for (String name : fillNameList) {
            Object object = null;
            switch (name) {
                case create_id:
                case update_id:
                    object = userInfo.getId();
                    break;
                case create_name:
                case update_name:
                    object = userInfo.getUsername();
                    break;
                case create_time:
                case update_time:
                    object = LocalDateTime.now();
                    break;
                default:
                    break;

            }
            // 判断是否存在该字段
            boolean bool = metaObject.hasSetter(name);
            if (bool) {
                // 判断字段是否为null
                Object value = metaObject.hasGetter(name) ? metaObject.getValue(name) : null;
                if (Objects.isNull(value)) {
                    if (Objects.nonNull(object) && metaObject.hasSetter(name)) {
                        metaObject.setValue(name, object);
                    }
                }
            }

        }

    }

}

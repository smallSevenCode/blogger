package org.example.test;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.example.common.ParentModel;
import org.junit.jupiter.api.Test;

import java.util.Collections;

/**
 * @author: 张鹏
 * @date: 2022/6/18 17:14
 **/
public class GeneratorTest {


    private final static String url = "jdbc:mysql://175.178.108.40:3306/blogger?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&serverTimezone=GMT%2b8";
    private final static String username = "root";
    private final static String password = "m0cgx2giij2c";
    private final static String[] tableList = {"tb_role_path"};
    private final static String parentPack = "org.example";


    @Test
    void test() {

        String property = System.getProperty("system.dir");

        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("张鹏") // 设置作者
                            .disableOpenDir()   // 关闭自动开发文件资源管理
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(property + "/src/main/java") // 指定输出目录
                            .build();
                })
                .packageConfig(builder -> {
                    builder.parent(parentPack) // 设置父包名
                            //.moduleName("") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, property + "/src/main/resources/mapper/"))  // 设置mapperXml生成路径
                            .build();
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tableList) // 设置需要生成的表名
                            .addTablePrefix("tb_") // 设置过滤表前缀
                            .build()
                            .entityBuilder()    // entity
                            .superClass(ParentModel.class)
                            .enableLombok()
                            .enableTableFieldAnnotation()
                            .addIgnoreColumns("update_time", "update_id", "update_name", "create_time", "create_id", "create_name")
                            .idType(IdType.ASSIGN_ID)
                            .build()
                            .controllerBuilder()    // controller
                            .enableRestStyle()
                            .mapperBuilder()    // mapper
                            .enableMapperAnnotation()
                            .build()
                            .serviceBuilder()   // service
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl")
                            .build();
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

}

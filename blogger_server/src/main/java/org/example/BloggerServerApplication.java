package org.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 启动类
 *
 * @author: 张鹏
 * @date: 2022/6/16 23:16
 **/
@Slf4j
@SpringBootApplication
public class BloggerServerApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(BloggerServerApplication.class, args);
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        log.info("\n---------------------------------------------------------\n\t" +
                        "应用 ‘{}’ 启动成功！访问链接: \n\t" +
                        "Swagger文档: \t\thttp://{}:{}{}/doc.html\n" +
                        "---------------------------------------------------------",
                environment.getProperty("spring.application.name"),
                InetAddress.getLocalHost().getHostAddress(),
                environment.getProperty("server.port"),
                environment.getProperty("server.servlet.context-path"));
    }


}

package com.chen.assistant.gatway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@SpringBootApplication
@ComponentScan("com.chen")
public class GatwayApplication {
    private static final Logger LOG = LoggerFactory.getLogger(GatwayApplication.class);
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(GatwayApplication.class);
        Environment env = app.run(args).getEnvironment();
        LOG.info("启动成功！！");
        LOG.info("网关地址：\thttp://127.0.0.1:{}", env.getProperty("server.port"));
    }
}

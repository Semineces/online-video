package com.hty.staservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * create by Semineces on 2020-08-28
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.hty"})
@MapperScan("com.hty.staservice.mapper")
@EnableFeignClients
@EnableDiscoveryClient
@EnableSwagger2
@EnableScheduling //开启定时任务
public class StaApplication {

    public static void main(String[] args) {
        SpringApplication.run(StaApplication.class, args);
    }
}

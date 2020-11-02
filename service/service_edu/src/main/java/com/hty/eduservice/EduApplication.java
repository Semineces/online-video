package com.hty.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * create by Semineces on 2020-08-04
 */
@SpringBootApplication
//如果不写 只能扫描当前项目 别的项目的扫描不到
@ComponentScan(basePackages = {"com.hty"})
@EnableSwagger2
//nacos注册
@EnableDiscoveryClient
//nacos进行调用 在调用端加
@EnableFeignClients
public class EduApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class);
    }
}

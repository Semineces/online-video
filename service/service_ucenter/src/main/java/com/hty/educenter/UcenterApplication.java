package com.hty.educenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * create by Semineces on 2020-08-18
 */
@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = {"com.hty"})
@MapperScan(basePackages = {"com.hty.educenter.mapper"})
@EnableDiscoveryClient
@EnableFeignClients
public class UcenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class, args);
    }

}

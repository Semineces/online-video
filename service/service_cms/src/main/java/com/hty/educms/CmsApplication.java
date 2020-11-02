package com.hty.educms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

/**
 * create by Semineces on 2020-08-14
 */

@SpringBootApplication
@ComponentScan({"com.hty"})
@MapperScan("com.hty.educms.mapper")
@EnableCaching
public class CmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}

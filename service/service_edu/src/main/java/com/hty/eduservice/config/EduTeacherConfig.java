package com.hty.eduservice.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * create by Semineces on 2020-08-04
 */
@Configuration
//这个配置类上写了mapperscan
@MapperScan("com.hty.eduservice.mapper")
public class EduTeacherConfig {

    /**
     * 逻辑删除插件，旧版本这么写，新版本直接配置properties就行
     * mybatis-plus。global-config。db-config。logic-delete-field=flag
     */
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}

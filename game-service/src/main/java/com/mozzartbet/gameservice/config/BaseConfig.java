package com.mozzartbet.gameservice.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.context.annotation.ComponentScan;
@Configuration
@MapperScan(basePackages = { "com.mozzartbet.gameservice.mapper" })
@ComponentScan(basePackages = { "com.mozzartbet.gameservice.domain" })
@EnableAsync
public class BaseConfig {
 
 
}

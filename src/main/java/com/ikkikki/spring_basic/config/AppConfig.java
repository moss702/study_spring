package com.ikkikki.spring_basic.config;

import com.ikkikki.spring_basic.domain.Member;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
public class AppConfig {
//  @Bean
//  public Member member() {
//    return new Member("SODDONG-I", 22);
//  }

  // 히카리 안쓰기로 했으니까 빈도 다 주석처리
//  @Bean
//  @ConfigurationProperties("spring.datasource.hikari")
//  public HikariConfig hikariConfig() {
//     return new HikariConfig();
//  }

//  @Bean
//  public HikariDataSource hikariDataSource() {
//    return new HikariDataSource(hikariConfig());
//  }
}

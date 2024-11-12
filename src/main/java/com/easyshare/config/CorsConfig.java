package com.easyshare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

// 前端已禁用同源策略，后端可以不设置跨域
@Configuration
public class CorsConfig {
    private CorsConfiguration corsConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOriginPattern("*");    // 允许所有的地址
        corsConfiguration.addAllowedHeader("*");           // 允许所有请求头
        corsConfiguration.addAllowedMethod("*");           // 允许所有mapping方法
        corsConfiguration.setAllowCredentials(true);       // 跨域请求时获取同一个 session
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig());
        return new CorsFilter(source);
    }
}

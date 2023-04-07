//package com.nccbc.digitalfreight.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @program: DigitalFreight
// * @description:
// * @author: Haochen Ren
// * @create: 2023-03-17 16:23
// **/
//@Configuration
//@Component
//public class CORSConfiguration {
//
//    @Bean
//    public CorsFilter corsFilter() {
////        CorsConfiguration config = new CorsConfiguration();
////        //允许所有域名进行跨域调用
////        config.addAllowedOrigin("*");
////        //允许所有请求头
////        config.addAllowedHeader("*");
////        //允许所有方法
////        config.addAllowedMethod("*");
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        source.registerCorsConfiguration("/**", config);
////        return new CorsFilter(source);
//        // 1.new一个CORS配置实例
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        // 1) 允许的域,不要写*，否则cookie就无法使用了
////        corsConfiguration.addAllowedOrigin("http://web.csp1999.com");
//        // 允许的域的集合
//        List<String> orginList = new ArrayList<>();
//        orginList.add("http://localhost:3000");
//        orginList.add("http://localhost:1235");
//        corsConfiguration.setAllowedOrigins(orginList);
//        // 2) 是否发送Cookie信息
//        corsConfiguration.setAllowCredentials(true);
//        // 3) 允许的请求方式
//        corsConfiguration.addAllowedMethod("OPTIONS");
//        corsConfiguration.addAllowedMethod("HEAD");
//        corsConfiguration.addAllowedMethod("GET");
//        corsConfiguration.addAllowedMethod("PUT");
//        corsConfiguration.addAllowedMethod("POST");
//        corsConfiguration.addAllowedMethod("DELETE");
//        corsConfiguration.addAllowedMethod("PATCH");
//        // 4）允许的头信息
//        corsConfiguration.addAllowedHeader("*");
//        // 2.添加映射路径，我们拦截一切请求
//        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
//        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
//        // 3.返回新的CorsFilter.
//        return new CorsFilter(urlBasedCorsConfigurationSource);
//    }
//}

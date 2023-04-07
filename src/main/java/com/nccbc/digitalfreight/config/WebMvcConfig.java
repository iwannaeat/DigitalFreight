//package com.nccbc.digitalfreight.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * @program: DigitalFreight
// * @description: Config
// * @author: Haochen Ren
// * @create: 2023-03-16 11:50
// **/
//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//    // 重写 【登陆拦截/拦截放行】
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // 下面这几行代码就是配置拦截器并放行部分接口
//        // registry.addInterceptor(new LoginHandlerInterceptor())
//        //         .addPathPatterns("/**")
//        //         .excludePathPatterns("/login.html","/u/login","/css","/*.js","/excel/add","/we_xin/test","/we_xin/test2");
//    }
//
//}

package com.nccbc.digitalfreight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//@SpringBootApplication(exclude= DataSourceAutoConfiguration.class)
//public class DigitalFreightApplication {
//
//    public static void main(String[] args) {
//        SpringApplication.run(DigitalFreightApplication.class, args);
//    }
//
//}

@SpringBootApplication
public class DigitalFreightApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DigitalFreightApplication.class);
    }

    public static void main(String[] args){
        SpringApplication.run(DigitalFreightApplication.class, args);
    }

}
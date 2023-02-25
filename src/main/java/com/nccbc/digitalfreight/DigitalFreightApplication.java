package com.nccbc.digitalfreight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude= DataSourceAutoConfiguration.class)
public class DigitalFreightApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalFreightApplication.class, args);
    }

}

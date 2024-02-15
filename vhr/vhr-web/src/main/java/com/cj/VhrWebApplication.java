package com.cj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.cj.*.mapper")
public class VhrWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(VhrWebApplication.class, args);
    }

}

package com.hhs;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hhs.mapper")
public class EaglemovieApplication {

    public static void main(String[] args) {
        SpringApplication.run(EaglemovieApplication.class, args);
    }

}

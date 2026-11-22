package com.example.xianxia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class XianxiaApplication {

    public static void main(String[] args) {
        SpringApplication.run(XianxiaApplication.class, args);
    }

}

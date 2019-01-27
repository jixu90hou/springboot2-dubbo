package com.winterchen;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("")
@EnableDubboConfiguration
@SpringBootApplication
public class Springboot2DubboApplication {

    @ResponseBody
    @GetMapping("")
    public String index() {
        return "hello world";
    }

    public static void main(String[] args) {
        SpringApplication.run(Springboot2DubboApplication.class, args);
    }
}

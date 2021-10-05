package com.home.closematch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Map;

@SpringBootApplication
@EnableSwagger2
@MapperScan("com.home.closematch.mapper")
public class ClosematchApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ClosematchApplication.class, args);
    }

}

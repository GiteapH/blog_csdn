package cn.springboot.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("cn.springboot.pioneermall.dao")
@SpringBootApplication
public class PioneerBlogAPIApplication {
    public static void main(String[] args) {
        SpringApplication.run(PioneerBlogAPIApplication.class, args);
    }
}

package org.qiujf;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.qiujf.entity.mapper")
public class LearnApplication {
    public static void main(String[] args) {
        SpringApplication.run(LearnApplication.class, args);
    }
}

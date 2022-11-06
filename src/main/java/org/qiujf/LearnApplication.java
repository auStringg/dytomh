package org.qiujf;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("org.qiujf.**.mapper")
@EnableScheduling
@Lazy
public class LearnApplication {
    public static void main(String[] args) {
        SpringApplication.run(LearnApplication.class, args);
    }
}

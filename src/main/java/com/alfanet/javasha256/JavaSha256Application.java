package com.alfanet.javasha256;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JavaSha256Application {

    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner();
    }
    
    public static void main(String[] args) {
        SpringApplication.run(JavaSha256Application.class, args);
    }
}

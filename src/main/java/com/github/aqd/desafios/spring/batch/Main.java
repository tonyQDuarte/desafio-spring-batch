package com.github.aqd.desafios.spring.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 
 * @author antonio.duarte
 *
 */
@SpringBootApplication
@EnableJpaRepositories
public class Main {
	
	public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
	
}

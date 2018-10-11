package com.copa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan(basePackages ="com.copa")
@SpringBootApplication
@EnableTransactionManagement
public class BiogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiogApplication.class, args);
	}
}

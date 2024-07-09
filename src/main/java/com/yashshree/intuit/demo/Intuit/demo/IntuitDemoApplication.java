package com.yashshree.intuit.demo.Intuit.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication()
@EnableJpaRepositories
@Configuration
//@ComponentScan("com")
public class IntuitDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntuitDemoApplication.class, args);
	}

}

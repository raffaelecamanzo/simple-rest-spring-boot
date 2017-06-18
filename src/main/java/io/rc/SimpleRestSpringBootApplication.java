package io.rc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@ComponentScan(basePackages = "io.rc")
public class SimpleRestSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleRestSpringBootApplication.class, args);
	}
}

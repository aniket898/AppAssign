package com.aniket.homework.componentservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(value = { "classpath:application-config.xml"})
@EnableAutoConfiguration
public class ComponentServiceApplication {

	private static final Logger logger = LoggerFactory.getLogger(ComponentServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ComponentServiceApplication.class, args);

	}

}

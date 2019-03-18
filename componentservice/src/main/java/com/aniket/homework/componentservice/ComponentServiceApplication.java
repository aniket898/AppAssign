package com.aniket.homework.componentservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@ImportResource(value = { "classpath:application-config.xml"})
@EnableAutoConfiguration
@EnableAsync
public class ComponentServiceApplication {

	private static final Logger logger = LoggerFactory.getLogger(ComponentServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ComponentServiceApplication.class, args);

	}

	@Bean(name = "DirectoryServiceTaskExecutor")
	public Executor directoryServiceTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("DirectoryServiceLookUp-");
		executor.initialize();
		return executor;
	}

}

package com.example.practiceBatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableBatchProcessing
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class PracticeBatchApplication {

	public static void main(String[] args) {
		// https://www.baeldung.com/spring-batch-tasklet-chunk
		SpringApplication.run(PracticeBatchApplication.class, args);
	}

}

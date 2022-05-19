package com.example.practiceBatch.taskletsvschunks.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.practiceBatch.taskletsvschunks.tasklets.LinesProcessor;
import com.example.practiceBatch.taskletsvschunks.tasklets.LinesReader;
import com.example.practiceBatch.taskletsvschunks.tasklets.LinesWriter;

@Configuration
public class TaskletsConfig {

	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private StepBuilderFactory steps;

	/*
	 * @Bean public JobLauncherTestUtils jobLauncherTestUtils() { return new
	 * JobLauncherTestUtils(); }
	 *
	 * @Bean public JobRepository jobRepository() throws Exception {
	 * JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
	 * factory.setDataSource(dataSource());
	 * factory.setTransactionManager(transactionManager()); return
	 * factory.getObject(); }
	 *
	 * @Bean public DataSource dataSource() { DriverManagerDataSource dataSource =
	 * new DriverManagerDataSource();
	 * dataSource.setDriverClassName("org.sqlite.JDBC");
	 * dataSource.setUrl("jdbc:sqlite:repository.sqlite"); return dataSource; }
	 *
	 * @Bean public PlatformTransactionManager transactionManager() { return new
	 * ResourcelessTransactionManager(); }
	 *
	 * @Bean public JobLauncher jobLauncher() throws Exception { SimpleJobLauncher
	 * jobLauncher = new SimpleJobLauncher();
	 * jobLauncher.setJobRepository(jobRepository()); return jobLauncher; }
	 */

	@Bean
	public LinesReader linesReader() {
		return new LinesReader();
	}

	@Bean
	public LinesProcessor linesProcessor() {
		return new LinesProcessor();
	}

	@Bean
	public LinesWriter linesWriter() {
		return new LinesWriter();
	}

	@Bean
	protected Step readLines() {
		return steps.get("readLines").tasklet(linesReader()).build();
	}

	@Bean
	protected Step processLines() {
		return steps.get("processLines").tasklet(linesProcessor()).build();
	}

	@Bean
	protected Step writeLines() {
		return steps.get("writeLines").tasklet(linesWriter()).build();
	}

	@Bean
	public Job job() {
		return jobs.get("taskletsJob").start(readLines()).next(processLines()).next(writeLines()).build();
	}
}
package com.example.practiceBatch.taskletsvschunks.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.practiceBatch.taskletsvschunks.chunks.LineProcessor;
import com.example.practiceBatch.taskletsvschunks.chunks.LineReader;
import com.example.practiceBatch.taskletsvschunks.chunks.LinesWriter;
import com.example.practiceBatch.taskletsvschunks.model.Line;

@Configuration
public class ChunksConfig {

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
	public ItemReader<Line> itemReader() {
		return new LineReader();
	}

	@Bean
	public ItemProcessor<Line, Line> itemProcessor() {
		return new LineProcessor();
	}

	@Bean
	public ItemWriter<Line> itemWriter() {
		return new LinesWriter();
	}

	@Bean
	protected Step processLines(ItemReader<Line> reader, ItemProcessor<Line, Line> processor, ItemWriter<Line> writer) {
		return steps.get("processLines").<Line, Line>chunk(2).reader(reader).processor(processor).writer(writer)
				.build();
	}

	@Bean
	public Job job() {
		return jobs.get("chunksJob").start(processLines(itemReader(), itemProcessor(), itemWriter())).build();
	}
}

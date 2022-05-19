package com.example.practiceBatch.taskletsvschunks;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.practiceBatch.taskletsvschunks.config.ChunksConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ChunksScheduler {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private ChunksConfig chunksConfig;

	@Scheduled(cron = "45 * * * * *")
	public void runJob() {
		// job parameter 설정
		Map<String, JobParameter> confMap = new HashMap<>();
		confMap.put("time", new JobParameter(System.currentTimeMillis()));
		JobParameters jobParameters = new JobParameters(confMap);

		try {
			jobLauncher.run(chunksConfig.job(), jobParameters);
		} catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException | JobRestartException e) {
			log.error(e.getMessage());
		}
	}
}

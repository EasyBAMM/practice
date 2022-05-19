package com.example.practiceBatch.taskletsvschunks.tasklets;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.example.practiceBatch.taskletsvschunks.model.Line;
import com.example.practiceBatch.taskletsvschunks.utils.FileUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LinesReader implements Tasklet, StepExecutionListener {

	private List<Line> lines;
	private FileUtils fu;

	@Override
	public void beforeStep(StepExecution stepExecution) {
		lines = new ArrayList<Line>();
		fu = new FileUtils("taskletsvschunks/input/tasklets-vs-chunks.csv");
		log.debug("Lines Reader initialized.");
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		Line line = fu.readLine();
		while (line != null) {
			lines.add(line);
			log.debug("Read line: " + line.toString());
			line = fu.readLine();
		}
		return RepeatStatus.FINISHED;
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		fu.closeReader();
		stepExecution.getJobExecution().getExecutionContext().put("lines", this.lines);
		log.debug("Lines Reader ended");
		return ExitStatus.COMPLETED;
	}
}

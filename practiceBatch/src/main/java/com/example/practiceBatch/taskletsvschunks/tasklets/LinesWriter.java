package com.example.practiceBatch.taskletsvschunks.tasklets;

import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

import com.example.practiceBatch.taskletsvschunks.model.Line;
import com.example.practiceBatch.taskletsvschunks.utils.FileUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LinesWriter implements Tasklet, StepExecutionListener {

	private List<Line> lines;
	private FileUtils fu;

	@Override
	public void beforeStep(StepExecution stepExecution) {
		ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
		this.lines = (List<Line>) executionContext.get("lines");
		fu = new FileUtils("output.csv");
		log.debug("Lines Writer initialized.");
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		for (Line line : lines) {
			fu.writeLine(line);
			log.debug("Wrote line " + line.toString());
		}
		return RepeatStatus.FINISHED;
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		fu.closeWriter();
		log.debug("Lines Writer ended.");
		return ExitStatus.COMPLETED;
	}

}
